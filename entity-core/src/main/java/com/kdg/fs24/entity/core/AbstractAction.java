package com.kdg.fs24.entity.core;

import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.core.api.ActionClassesPackages;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.kdg.fs24.application.core.nullsafe.StopWatcher;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.Data;
import com.kdg.fs24.entity.core.api.SkipRefresh;
import com.kdg.fs24.spring.core.api.ServiceLocator;

/**
 *
 * @author kazyra_d
 */
@Data
public abstract class AbstractAction<T extends ActionEntity>
        extends AbstractPersistenceAction {

    // объекты для персистенса
    private PersistanceEntityManager persistanceEntityManager;

    private PersistanceEntityManager getPersistanceEntityManager() {

        if (NullSafe.isNull(this.persistanceEntityManager)) {
            persistanceEntityManager = ServiceLocator.<PersistanceEntityManager>findService(PersistanceEntityManager.class);
        }

        return this.persistanceEntityManager;
    }

    //==========================================================================
    private final Collection<PersistenceEntity> persistenceEntities
            = ServiceFuncs.<PersistenceEntity>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

    private StopWatcher stopWatcher;
    private String errMsg;
    private AbstractPersistenceAction persistAction;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void execute() {

        this.initialize();
        this.doCalculation();
        this.afterCalculation();

        if (this.isValid()) {
            this.stopWatcher = StopWatcher.create();
            this.beforeUpdate();
            // наполнение в предках объектов для персистенса
            //this.doUpdate();

            //final AbstractPersistenceAction<T> ent2persist = NullSafe.createObject(AbstractPersistenceAction.class); 
            // сохранили объекты
            getPersistanceEntityManager()
                    .executeTransaction(em -> {

                        NullSafe.create()
                                .execute(() -> {
                                    // создание базовой сущности
                                    this.createMainEntity();

                                    // создание действия
                                    this.createPersistenceAction();

                                    // модификация
                                    this.doUpdate();

                                    persistenceEntities
                                            .forEach((obj) -> {

                                                if (NullSafe.isNull(this.getErrMsg())) {

                                                    if (obj instanceof AbstractPersistenceEntity) {
                                                        final AbstractPersistenceEntity apeObj = (AbstractPersistenceEntity) (obj);

                                                        apeObj.setLast_modify(this.getExecuteDate());
                                                    }

                                                    if (!obj.justCreated()) {
                                                        em.merge(obj);
                                                    } else {
                                                        em.persist(obj);
                                                    }
                                                }
                                            });

                                }).catchException(e -> this.setErrMsg(String.format("%s: %s", this.getClass().getSimpleName(), NullSafe.getErrorMessage(e))))
                                .finallyBlock(() -> {
                                    // обновление действия
                                    this.updatePersistenceAction();
                                    this.updateMainEntity();
                                });
                        this.getPersistanceEntityManager()
                                .getEntityManager()
                                .flush();
                    });

            if (NullSafe.notNull(this.getErrMsg())) {

                throw new ActionExecutionException(this.getErrMsg());
            }

            this.afterCommit();
            this.refreshModifiedEntities();
        }
    }

    //==========================================================================
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void registerActionFail(final Throwable th) {
        getPersistanceEntityManager()
                .executeTransaction(em -> {

                    this.setErrMsg(String.format("%s: \n %s",
                            NullSafe.getErrorMessage(th),
                            NullSafe.getStackTraceRaw(th)));
                    this.updatePersistenceAction();
                });
    }

    //==========================================================================
    private void createMainEntity() {

        if (this.getEntity().justCreated()) {

            ((AbstractPersistenceEntity) this.getEntity()).setCreation_date(LocalDateTime.now());

            getPersistanceEntityManager()
                    .getEntityManager()
                    .persist(this.getEntity());
        }
    }

    //==========================================================================
    private void updateMainEntity() {
        final AbstractPersistenceEntity obj = (AbstractPersistenceEntity) this.getEntity();

        obj.setLast_modify(this.getPersistAction().getExecuteDate());

        getPersistanceEntityManager().getEntityManager().persist(obj);

    }

    //==========================================================================
    private void createPersistenceAction() {
        this.setPersistAction(getPersistanceEntityManager().<AbstractPersistenceAction>createPersistenceEntity(
                AbstractPersistenceAction.class,
                (action) -> {
                    action.setEntity(this.getEntity());
                    action.setActionCode(this.getActionCode());
                    action.setActionDuration(LocalTime.MIN);
//                    action.setErrMsg(this.getErrMsg());
                }));
    }

    //==========================================================================
    private void updatePersistenceAction() {
        this.getPersistAction()
                .setActionDuration(LocalTime.MIN.plus(this.stopWatcher.getTimeExecMillis(), ChronoUnit.MILLIS));
        this.getPersistAction()
                .setErrMsg(this.getErrMsg());
        this.getPersistanceEntityManager()
                .getEntityManager()
                .merge(this.getPersistAction());
    }

    //==========================================================================
    protected void addPersistenceEntity(final PersistenceEntity persistenceEntity) {
        this.persistenceEntities.add(persistenceEntity);
    }

    //==========================================================================
    protected void doUpdate() {

    }

    //==========================================================================
    protected void afterCommit() {

    }

    //==========================================================================
    public void refreshModifiedEntities() {

        Boolean needRefresh = SysConst.BOOLEAN_TRUE;

        if (AnnotationFuncs.isAnnotated(this.getClass(), SkipRefresh.class)) {
            needRefresh = !AnnotationFuncs.<SkipRefresh>getAnnotation(this.getClass(), SkipRefresh.class).skipRefresh();
        }

        if (needRefresh) {

            if (SysConst.DEBUG_MODE.get()) {

                LogService.LogInfo(this.getClass(), () -> String.format("refresh entity (%d, %s)",
                        this.getEntity().entityId(),
                        this.getEntity().getClass().getSimpleName())
                        .toUpperCase());
            }
            // обновление кэша
            getPersistanceEntityManager()
                    .getFactory()
                    .getCache()
                    .evict(this.getEntity().getClass(),
                            this.getEntity().entityId());

            // поиск сущности
//            final ActionEntity entity = persistanceEntityManager
//                    .getEntityManager()
//                    .find(this.getEntity().getClass(), this.getEntity().entityId());
            // обновление главной сущности
            getPersistanceEntityManager()
                    .getEntityManager()
                    .refresh(this.getEntity());

            if (SysConst.DEBUG_MODE.get()) {
                LogService.LogInfo(this.getClass(), () -> String.format("refresh entity is finished (%d, %s)",
                        this.getEntity().entityId(),
                        this.getEntity().getClass().getSimpleName())
                        .toUpperCase());
            }

            // обновление подчиненных сущности
//            persistenceEntities
//                    .forEach((obj) -> {
//
//                        LogService.LogInfo(this.getClass(), () -> String.format("refresh persistene entity (%s)",
//                                obj.getClass().getSimpleName()).toUpperCase());
//
//                        persistanceEntityManager
//                                .getEntityManager()
//                                .refresh(obj);
//                    });
        }
    }

    protected void doCalculation() {

    }

    protected void afterCalculation() {

    }

    protected void beforeUpdate() {

    }

    protected boolean isValid() {

        return SysConst.BOOLEAN_TRUE;
    }

    public void initialize() {

    }

    protected void finallyExecute() {

    }

    protected void strongExecute() {

    }

    protected void insertErrMsg(final String errMsg) {
//        getDbService()
//                .createCallQuery("{call core_insert_errmsg(:AC, :ERR)}")
//                .setParamByName("AC", getActionId())
//                .setParamByName("ERR", errMsg)
//                .execCallStmt();
    }

    public static Boolean isAllowed(final AbstractActionEntity entity, final Integer allowedStatus) {

        return (NullSafe.create()
                .execute2result(() -> (entity.getEntityStatus().getEntityStatusId().equals(allowedStatus))))
                .catchException2result((e) -> SysConst.IS_NOT_ALLOWED_ACTION)
                .<Boolean>getObject();

//        return (entity.getEntityStatus().getEntity_status_id().equals(allowedStatus));
    }

}

class ActionExecutionException extends InternalAppException {

    public ActionExecutionException(final String message) {
        super(message);
    }
}
