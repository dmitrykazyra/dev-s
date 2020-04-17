/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
import com.kdg.fs24.spring.core.api.ApplicationRepositoryService;
import lombok.Data;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import com.kdg.fs24.entity.core.api.EntityClassesPackages;
import com.kdg.fs24.entity.core.api.ActionClassesPackages;
import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.entity.core.AbstractAction;
import com.kdg.fs24.entity.core.api.ActionCodeId;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import com.kdg.fs24.repository.ActionCodesRepository;
import com.kdg.fs24.entity.action.ActionCode;
import com.kdg.fs24.entity.core.api.EntityKindId;

/**
 *
 * @author N76VB
 */
@Data
//@Service

public abstract class ActionExecutionService
        implements ApplicationRepositoryService {

    private final Map<Class<ENT>, Class<ACT>> CLASS_ENT2ACTION
            = ServiceFuncs.getOrCreateMap(ServiceFuncs.MAP_NULL);

    private final Map<Integer, Class<ACT>> CLASS_INT2ACTION
            = ServiceFuncs.getOrCreateMap(ServiceFuncs.MAP_NULL);

    @Value("${debug}")
    private String debugMode; // = SysConst.STRING_FALSE;

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    @Autowired
    private ActionCodesRepository actionCodesRepository;

    @Autowired
    private EntityReferencesService entityReferencesService;

    //==========================================================================
    // выполнение действия над сущностью
    public void executeAction(final AbstractActionEntity entity, final Integer action_code) {
        final Optional<Class<ACT>> optActClass = ServiceFuncs.getMapValue(CLASS_INT2ACTION, mapEntry -> mapEntry.getKey().equals(action_code));

        if (!optActClass.isPresent()) {
            throw new UnknownActionCode(String.format("Unknown action_code (%d)", action_code));
        }

        final Class actClass = optActClass.get();

        final AbstractAction action = NullSafe.<AbstractAction>createObject(actClass);

        final Optional<ActionCode> ac = actionCodesRepository.findById(action_code);

        if (!ac.isPresent()) {
            throw new NoActionCodeDefined(String.format("Unknown actionCode (%d)", action_code));
        }

        action.setPersistanceEntityManager(persistanceEntityManager);
        action.setEntity(entity);
        action.setActionCode(ac.get());

        //action.execute(entity, ac.get());
        action.execute();
    }

    //==========================================================================
    @PostConstruct
    public void postActionExecutionService() {
        final Class thisClass = this.getClass();

        if (!AnnotationFuncs.isAnnotated(thisClass, EntityClassesPackages.class)) {
            throw new NoEntityClassesPackagesDefined(String.format("No EntityClassesPackages annotations defined for '%s' ",
                    thisClass.getCanonicalName()));
        }

        final String[] entityClassesPackages = AnnotationFuncs.<EntityClassesPackages>getAnnotation(thisClass, EntityClassesPackages.class).pkgList();

        // выясняем 
        Arrays.stream(entityClassesPackages)
                .forEach(entPkg -> {
                    ReflectionFuncs.createPkgClassesCollection(entPkg, AbstractActionEntity.class)
                            .stream()
                            .filter(p -> !p.isInterface())
                            .filter(p -> !Modifier.isAbstract(p.getModifiers()))
                            .filter(p -> AnnotationFuncs.isAnnotated(p, EntityTypeId.class))
                            .forEach((entClazz) -> {

                                // действия на сущности
                                if (!AnnotationFuncs.isAnnotated(entClazz, ActionClassesPackages.class)) {
                                    throw new NoActionClassesPackagesDefined(String.format("No ActionClassesPackagesDefined annotations defined for '%s' ",
                                            entClazz.getCanonicalName()));
                                }

                                final String[] actionsClassesPackages = AnnotationFuncs.<ActionClassesPackages>getAnnotation(entClazz, ActionClassesPackages.class).pkgList();
                                //this.RegisterEntityClass((Class< T>) clazz);
                                Arrays.stream(actionsClassesPackages)
                                        .forEach(actPkg -> {

                                            ReflectionFuncs.createPkgClassesCollection(actPkg, AbstractAction.class)
                                                    .stream()
                                                    .filter(p -> !p.isInterface())
                                                    .filter(p -> !Modifier.isAbstract(p.getModifiers()))
                                                    .filter(p -> AnnotationFuncs.isAnnotated(p, ActionCodeId.class))
                                                    .forEach((actClazz) -> {
                                                        this.registerEntClass(entClazz,
                                                                actClazz,
                                                                AnnotationFuncs.getAnnotation(actClazz, ActionCodeId.class).action_code());
                                                    });
                                        });
                            });
                });

        if (this.CLASS_ENT2ACTION.isEmpty()) {
            throw new NoActionClassesDefined(String.format("Action classes is empty for '%s' ",
                    thisClass.getCanonicalName()));
        }

        // post
        LogService.LogInfo(thisClass, () -> String.format("There %d pair(s) (entities/action): '%s' ",
                this.CLASS_ENT2ACTION.size(),
                this.getClass().getCanonicalName()));

        // синхронизируем с БД
    }
    //==========================================================================

    private void registerEntClass(final Class entClass, final Class actClass, final Integer action_code) {
        if (this.debugMode.equals(SysConst.STRING_TRUE)) {
            LogService.LogInfo(this.getClass(), () -> String.format("Add entity class/action: %s->%s",
                    entClass.getCanonicalName(),
                    actClass.getCanonicalName()));
        }
        this.CLASS_ENT2ACTION.put(entClass, actClass);
        this.CLASS_INT2ACTION.put(action_code, actClass);

        // обновляем справочники БД
        //======================================================================
        final EntityTypeId entityTypeId = AnnotationFuncs.<EntityTypeId>getAnnotation(entClass, EntityTypeId.class);

        if (NullSafe.notNull(entityTypeId)) {

            entityReferencesService.createNewEntityType(entityTypeId.entity_type_id(),
                    entityTypeId.entity_type_name(),
                    getModuleName(entClass.getProtectionDomain().getCodeSource().getLocation().getFile()));

        }
        //======================================================================
        final EntityKindId entityKindId = AnnotationFuncs.<EntityKindId>getAnnotation(entClass, EntityKindId.class);

        if (NullSafe.notNull(entityKindId)) {

            entityReferencesService.createNewEntityKind(entityKindId.entity_kind_id(),
                    entityKindId.entity_type_id(),
                    entityKindId.entity_kind_name());

        }
        //======================================================================
        final ActionCodeId actionCodeId = AnnotationFuncs.<ActionCodeId>getAnnotation(actClass, ActionCodeId.class);
        if (NullSafe.notNull(actionCodeId)) {

            entityReferencesService.createNewActionCode(actionCodeId.action_code(),
                    actionCodeId.action_name(),
                    getModuleName(entClass.getProtectionDomain().getCodeSource().getLocation().getFile()),
                    Boolean.FALSE);

        }
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
}
//==============================================================================

class NoActionCodeDefined extends InternalAppException {

    public NoActionCodeDefined(final String message) {
        super(message);
    }
}

class NoEntityClassesPackagesDefined extends InternalAppException {

    public NoEntityClassesPackagesDefined(final String message) {
        super(message);
    }
}

class NoActionClassesPackagesDefined extends InternalAppException {

    public NoActionClassesPackagesDefined(final String message) {
        super(message);
    }
}

//==============================================================================
class NoActionClassesDefined extends InternalAppException {

    public NoActionClassesDefined(final String message) {
        super(message);
    }
}

//==============================================================================
class UnknownActionCode extends InternalAppException {

    public UnknownActionCode(final String message) {
        super(message);
    }
}

class ENT extends AbstractActionEntity {

}

class ACT extends AbstractAction {

}
