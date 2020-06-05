/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
//import com.kdg.fs24.spring.core.api.ApplicationRepositoryService;
import lombok.Data;
import lombok.AllArgsConstructor;
import com.kdg.fs24.entity.core.AbstractPersistenceEntity;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import com.kdg.fs24.entity.core.api.EntityClassesPackages;
import com.kdg.fs24.entity.core.api.ActionClassesPackages;
import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.nullsafe.StopWatcher;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.entity.core.AbstractAction;
import com.kdg.fs24.entity.core.api.ActionCodeId;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import java.util.Arrays;
import java.util.Map;
import java.util.Collection;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import com.kdg.fs24.entity.action.ActionCode;
import com.kdg.fs24.entity.core.api.EntityKindId;
import com.kdg.fs24.entity.core.api.EntityStatusesRef;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.entity.kind.EntityKind;
import com.kdg.fs24.entity.marks.Mark;
import com.kdg.fs24.entity.marks.MarkValue;
import com.kdg.fs24.entity.status.EntityStatusId;
import com.kdg.fs24.references.api.AbstractRefRecord;
import java.lang.annotation.Annotation;
import com.kdg.fs24.entity.core.api.CachedReferencesClasses;
import com.kdg.fs24.references.api.ReferenceSyncOrder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Method;

/**
 *
 * @author N76VB
 */
@Data
//@Service
@CachedReferencesClasses(classes = {EntityStatus.class, EntityKind.class, ActionCode.class, Mark.class, MarkValue.class})
public abstract class ActionExecutionService extends AbstractApplicationService {

    //==========================================================================
    private final Collection<Pair> CLASS_ENT2ACTION
            = ServiceFuncs.createCollection();

    private final Collection<? extends AbstractActionEntity> enityCollection
            = ServiceFuncs.createCollection();

    // сущность - действие
//    private final Map<Class<ENT>, Class<ACT>> CLASS_ENT2ACTION
//            = ServiceFuncs.getOrCreateMap_Safe(ServiceFuncs.MAP_NULL);
    // действие - номер действия
    private final Map<Integer, Class<ACT>> CLASS_INT2ACTION
            = ServiceFuncs.getOrCreateMap_Safe(ServiceFuncs.MAP_NULL);

    // сущность - статус сущности
    private final Map<Class<ENT>, Integer> CLASS_ENT2STATUS
            = ServiceFuncs.getOrCreateMap_Safe(ServiceFuncs.MAP_NULL);

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

//    @Autowired
//    private ActionCodesRepository actionCodesRepository;
    @Autowired
    private EntityReferencesService entityReferencesService;

    //==========================================================================
    class IllegalActionForEntity extends InternalAppException {

        public IllegalActionForEntity(final String message) {
            super(message);
        }
    }

    class UnknownActionCode extends InternalAppException {

        public UnknownActionCode(final String message) {
            super(message);
        }
    }

    // выполнение действия над сущностью
    public void executeAction(
            final AbstractActionEntity entity,
            final Integer action_code) {

        this.<AbstractAction>executeAction(entity, action_code, (action) -> {
        });
    }

    // выполнение действия над сущностью
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public <T extends AbstractAction> void executeAction(
            final AbstractActionEntity entity,
            final Integer action_code,
            final ActionInitializer<T> actionInitializer) {
        final Class actClass = ServiceFuncs.getMapValue(
                CLASS_INT2ACTION,
                mapEntry -> mapEntry.getKey().equals(action_code))
                .orElseThrow(() -> new UnknownActionCode(String.format("Unknown action_code (%d)", action_code)));

        // находим класс среди допустимых
        CLASS_ENT2ACTION
                .stream()
                .filter(pair -> pair.getEntClass().equals(entity.getClass()))
                .filter(pair -> pair.getActClass().equals(actClass))
                .findFirst()
                .orElseThrow(() -> new IllegalActionForEntity(String.format("Action '%s' is not allowed for entity (%s)",
                entity, actClass)));

        // экземплр действия
        final AbstractAction action = NullSafe.<T>createObject(actClass);

        final ActionCode ac = ActionCode.findActionCode(action_code);

//        if (!ac.isPresent()) {
//            throw new NoActionCodeDefined(String.format("Unknown actionCode (%d)", action_code));
//        }
        //action.setPersistanceEntityManager(persistanceEntityManager);
        action.setEntity(entity);
        action.setActionCode(ac);

        // дополнительная инициализация действия
        actionInitializer.initialize((T) action);

        //action.execute(entity, ac.get());
        NullSafe.create()
                .execute(() -> action.execute())
                .catchException(e -> action.registerActionFail(e))
                .throwException();
        //action.refreshModifiedEntities();
    }

    //==========================================================================
    @PostConstruct
    public void postActionExecutionService() {

        if (!AnnotationFuncs.isAnnotated(this.getClass(), EntityClassesPackages.class)) {
            class NoEntityClassesPackagesDefined extends InternalAppException {

                public NoEntityClassesPackagesDefined(final String message) {
                    super(message);
                }
            }
            throw new NoEntityClassesPackagesDefined(String.format("No EntityClassesPackages annotations defined for '%s' ",
                    this.getClass().getCanonicalName()));
        }

        final String[] entityClassesPackages = AnnotationFuncs.<EntityClassesPackages>getAnnotation(this.getClass(), EntityClassesPackages.class).pkgList();

        // классы сущностей
        Arrays.stream(entityClassesPackages)
                .forEach(entPkg -> {

                    final Class<AbstractActionEntity> clazz = (AbstractActionEntity.class);
                    final Class<EntityTypeId> annClazz = EntityTypeId.class;

                    // значения для справочника берутся из аннотаций классов
                    ReflectionFuncs.processPkgClassesCollection(entPkg, clazz, annClazz,
                            (entClazz) -> {

                                // действия на сущности
                                if (!AnnotationFuncs.isAnnotated(entClazz, ActionClassesPackages.class)) {
                                    class NoActionClassesPackagesDefined extends InternalAppException {

                                        public NoActionClassesPackagesDefined(final String message) {
                                            super(message);
                                        }
                                    }
                                    throw new NoActionClassesPackagesDefined(String.format("No ActionClassesPackagesDefined annotations defined for '%s' ",
                                            entClazz.getCanonicalName()));
                                }

                                final String[] actionsClassesPackages = AnnotationFuncs.<ActionClassesPackages>getAnnotation(entClazz, ActionClassesPackages.class).pkgList();
                                //this.RegisterEntityClass((Class< T>) clazz);
                                Arrays.stream(actionsClassesPackages)
                                        .forEach(actPkg -> {

                                            final Class<AbstractAction> clazzAct = (AbstractAction.class);
                                            final Class<ActionCodeId> annActClazz = ActionCodeId.class;

                                            // значения для справочника берутся из аннотаций классов
                                            ReflectionFuncs.processPkgClassesCollection(actPkg, clazzAct, annActClazz,
                                                    (actClazz) -> {
                                                        this.registerEntClass(entClazz,
                                                                actClazz,
                                                                AnnotationFuncs.getAnnotation(actClazz, annActClazz).action_code());
                                                    });
                                        });

                                // статусы сущностей
                                if (!AnnotationFuncs.isAnnotated(entClazz, EntityStatusesRef.class)) {
                                    class NoEntityStatusesDefined extends InternalAppException {

                                        public NoEntityStatusesDefined(final String message) {
                                            super(message);
                                        }
                                    }
                                    throw new NoEntityStatusesDefined(String.format("No EntityStatuses defined annotations defined for '%s' ",
                                            entClazz.getCanonicalName()));
                                }

                                final EntityStatusId[] entityStatuses = AnnotationFuncs.<EntityStatusesRef>getAnnotation(entClazz, EntityStatusesRef.class).entiy_status();

                                Arrays.stream(entityStatuses)
                                        .forEach(entityStatus -> {
                                            this.registerEntStatus(entClazz, entityStatus);
                                        });
                            });
                }
                );

        if (this.CLASS_ENT2ACTION.isEmpty()) {
            class NoActionClassesDefined extends InternalAppException {

                public NoActionClassesDefined(final String message) {
                    super(message);
                }
            }
            throw new NoActionClassesDefined(String.format("Action classes is empty for '%s' ",
                    this.getClass().getCanonicalName()));
        }

        // post
        LogService.LogInfo(
                this.getClass(),
                () -> String.format("There [%d] pair(s) (entities/action): '%s' ",
                        this.CLASS_ENT2ACTION.size(),
                        this.getClass().getCanonicalName()));

        // синхронизируем с БД
    }
    //==========================================================================

    private void registerEntClass(final Class entClass, final Class actClass, final Integer action_code) {
        if (SysConst.DEBUG_MODE.get()) {
            LogService.LogInfo(this.getClass(), () -> String.format("Add entity class/action: %s->%s",
                    entClass.getCanonicalName(),
                    actClass.getCanonicalName()));
        }
        //this.CLASS_ENT2ACTION.put(entClass, actClass);

        this.CLASS_ENT2ACTION.add(new Pair(entClass, actClass));
        this.CLASS_INT2ACTION.put(action_code, actClass);

        // обновляем справочники БД
        //======================================================================
        final EntityTypeId entityTypeId = AnnotationFuncs.<EntityTypeId>getAnnotation(entClass, EntityTypeId.class
        );

        if (NullSafe.notNull(entityTypeId)) {

            entityReferencesService.createNewEntityType(entityTypeId.entity_type_id(),
                    entityTypeId.entity_type_name(),
                    getModuleName(entClass.getProtectionDomain().getCodeSource().getLocation().getFile()));

        }
        //======================================================================
        final EntityKindId entityKindId = AnnotationFuncs.<EntityKindId>getAnnotation(entClass, EntityKindId.class
        );

        if (NullSafe.notNull(entityKindId)) {

            entityReferencesService.createNewEntityKind(entityKindId.entity_kind_id(),
                    entityKindId.entity_type_id(),
                    entityKindId.entity_kind_name());

        }
        //======================================================================
        final ActionCodeId actionCodeId = AnnotationFuncs.<ActionCodeId>getAnnotation(actClass, ActionCodeId.class
        );
        if (NullSafe.notNull(actionCodeId)) {

            entityReferencesService.createNewActionCode(actionCodeId.action_code(),
                    actionCodeId.action_name(),
                    getModuleName(entClass.getProtectionDomain().getCodeSource().getLocation().getFile()),
                    Boolean.FALSE);
        }
    }

    //==========================================================================
    private void registerEntStatus(final Class entClass, final EntityStatusId entityStatusId) {
        CLASS_ENT2STATUS.put(entClass, entityStatusId.entity_status_id());

        entityReferencesService.createNewEntityStatus(entityStatusId.entity_status_id(),
                entityStatusId.entity_type_id(),
                entityStatusId.entity_status_name());
    }

    //==========================================================================    
    protected static String getModuleName(final String classUrl) {

        String moduleName = SysConst.EMPTY_STRING;
        String url = classUrl;

        if ((url.lastIndexOf("/target/") < 0)) {
            while (moduleName.lastIndexOf(".") < 0) {

                if (!moduleName.isEmpty()) {
                    url = url.substring(0, url.lastIndexOf("/WEB-INF"));
                }

                moduleName = url.substring(url.lastIndexOf("/") + 1);

            }
        }

        return moduleName;
    }

    //==========================================================================
    public <T extends AbstractActionEntity> T createActionEntity(final Class<T> entClass,
            final ActionEntityCreator<T> aec) {

        final T actionEntity = NullSafe.<T>createObject(entClass);

        aec.createEntity(actionEntity);

        return actionEntity;

    }

    //==============================================================================
    @PostConstruct
    public void loadSysReferences() {

        Class clAss = this.getClass();

        while (NullSafe.notNull(clAss)) {

            final Annotation annotation = AnnotationFuncs.<CachedReferencesClasses>getAnnotation(clAss, CachedReferencesClasses.class
            );

            if (NullSafe.notNull(annotation)) {

                final Class[] classes = AnnotationFuncs.<CachedReferencesClasses>getAnnotation(clAss, CachedReferencesClasses.class
                ).classes();

                Arrays.stream(classes)
                        .sorted((refClass1, refClass2) -> { // достаем признак порядкового номера из аннотации

                            final Integer order_num1 = (NullSafe.create(SysConst.OBJECT_NULL, NullSafe.DONT_THROW_EXCEPTION)
                                    .execute2result(() -> {
                                        return ((ReferenceSyncOrder) AnnotationFuncs.getAnnotation(refClass1, ReferenceSyncOrder.class
                                        )).order_num();
                                    }, Integer.valueOf("10000"))).<Integer>getObject();

                            final Integer order_num2 = (NullSafe.create(SysConst.OBJECT_NULL, NullSafe.DONT_THROW_EXCEPTION)
                                    .execute2result(() -> {
                                        return ((ReferenceSyncOrder) AnnotationFuncs.getAnnotation(refClass2, ReferenceSyncOrder.class
                                        )).order_num();
                                    }, Integer.valueOf("10000"))).<Integer>getObject();

                            return order_num1.compareTo(order_num2);
                        })
                        .forEach(clazz -> {

                            if (!ServiceFuncs.getMapValue(AbstractRefRecord.REF_CACHE, mapEntry -> mapEntry.getKey().equals(clazz)).isPresent()) {

                                // синхронизировать справочник в БД
                                if (SysConst.BOOLEAN_FALSE) {
//                               if (SysConst.BOOLEAN_TRUE) {
                                    NullSafe.create(this.findRegisterMethod(clazz, "getActualReferencesList"))
                                            .safeExecute((ns_method) -> {
                                                synchronized (clazz) {

                                                    final String key = String.format("%s_%s",
                                                            LogService.getCurrentObjProcName(this),
                                                            clazz.getCanonicalName());
                                                    LogService.LogInfo(this.getClass(), key, () -> clazz.getCanonicalName());
                                                    // коллекция записей справочника
                                                    final Collection collection = (Collection) ((Method) ns_method).invoke(null);
                                                    // сохранение в бд
                                                    getPersistanceEntityManager()
                                                            .executeTransaction(em -> {
                                                                collection
                                                                        .stream()
                                                                        .forEach(record -> {
                                                                            em.merge(record);
                                                                        });
                                                            });
                                                }
                                            }).throwException();
                                }

                                //final String tableName = AnnotationFuncs.<Table>getAnnotation(clazz, Table.class).name();
                                NullSafe.create()
                                        .execute((stmt) -> {
                                            final Collection<? extends AbstractRefRecord> collection = this.getPersistanceEntityManager()
                                                    .getEntityManager()
                                                    .createQuery("Select t from " + clazz.getSimpleName() + " t")
                                                    .getResultList();

                                            AbstractRefRecord.REF_CACHE.put(clazz, collection);

                                            LogService.LogInfo(clazz, () -> String.format("Reference '%s' is loaded ",
                                                    clazz.getCanonicalName()));

                                        })
                                        .catchException(e
                                                -> LogService.LogErr(clazz, () -> String.format("Can't load refernce '%s' (%s) ",
                                        clazz.getCanonicalName(), e.getMessage())))
                                        .throwException();
                            }
                        });
            }
            clAss = clAss.getSuperclass();
        }

        LogService.LogInfo(this.getClass(), () -> String.format("There are [%d] system reference(s) loaded '%s' ",
                AbstractRefRecord.REF_CACHE.size(),
                this.getClass().getCanonicalName()));
    }

    //--------------------------------------------------------------------------
    private Method findRegisterMethod(final Class clazz, final String methodName) {
        final String key = String.format("%s_%s.%s",
                LogService.getCurrentObjProcName(this),
                clazz.getCanonicalName(),
                methodName);

        return (NullSafe.create()
                .execute2result(() -> {
                    return clazz.getMethod(methodName);
                })
                .catchMsgException((errMsg) -> {
                    LogService.LogErr(clazz, key,
                            () -> String.format("methodName not found ('%s', class='%s') (%s)",
                                    methodName,
                                    clazz.getCanonicalName(),
                                    errMsg));
                }))
                .<Method>getObject();
    }

    //==========================================================================
//    public <T extends AbstractActionEntity> Optional<T> findActionEntity(
//            final Class<T> entClass,
//            final Long entityId) {
//
//        final Optional<T> optEntity = ServiceFuncs.<T>getCollectionElement(enityCollection
//                .stream()
//                .filter(entity -> entity.getClass().equals(entClass))
//                .map(entity -> (T) entity)
//                .collect(Collectors.toList()),
//                entity -> entity.getEntity_id().equals(entityId)
//        );
//
//        final T entity;
//
//        if (optEntity.isPresent()) {
//            // найдено в кэше
//            entity = (T) optEntity.get();
//        } else {
//            // ищем в БД
//            entity = persistanceEntityManager
//                    .getEntityManager()
//                    .find(entClass, entityId);
//        }
//
//        return Optional.ofNullable(entity);
//
//    }
    public <T extends AbstractPersistenceEntity> T reloadTestedEntity(final Class<T> entClass,
            final Long entityId) {
        
        this.getPersistanceEntityManager()
                .getEntityManager()
                .clear();

        final StopWatcher stopWatcher = StopWatcher.create();

        LogService.LogInfo(this.getClass(), () -> String.format("try 2 reload created entity (%d)",
                entityId));

        // поиск сущности
        final T entity = this.getPersistanceEntityManager()
                .getEntityManager()
                .<T>find(entClass, entityId);

//        this.getPersistanceEntityManager()
//                .getEntityManager()
//                .refresh(entity);
        LogService.LogInfo(this.getClass(), () -> String.format("Refresh entity is finished (%d, %d ms)",
                entity.entityId(),
                stopWatcher.getTimeExecMillis()));

        return entity;

    }
}
//==============================================================================

@AllArgsConstructor
@Data
final class Pair {

    private Class<ENT> entClass;
    private Class<ACT> actClass;
}

class NoActionCodeDefined extends InternalAppException {

    public NoActionCodeDefined(final String message) {
        super(message);
    }
}

class ENT extends AbstractActionEntity {

}

class ACT extends AbstractAction {

}
