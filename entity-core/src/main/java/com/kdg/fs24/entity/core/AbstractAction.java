package com.kdg.fs24.entity.core;

import com.kdg.fs24.entity.core.api.Action;
import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.action.ActionCode;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.kdg.fs24.tce.api.StopWatcher;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.Data;
import java.util.Optional;

/**
 *
 * @author kazyra_d
 */
@Data
public abstract class AbstractAction<T extends ActionEntity>
        extends AbstractPersistenceAction {

    // объекты для персистенса
    private PersistanceEntityManager persistanceEntityManager;

    private final Collection<PersistenceEntity> persistenceObjects
            = ServiceFuncs.<PersistenceEntity>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

    private StopWatcher stopWatcher;
    private String errMsg;
    private AbstractPersistenceAction persistAction;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void execute() {

        this.doCalculation();
        this.afterCalculation();

        if (this.isValid()) {
            this.stopWatcher = StopWatcher.create();
            this.beforeUpdate();
            // наполнение в предках объектов для персистенса
            //this.doUpdate();

            //final AbstractPersistenceAction<T> ent2persist = NullSafe.createObject(AbstractPersistenceAction.class); 
            // сохранили объекты
            persistanceEntityManager
                    .executeTransaction(em -> {

                        NullSafe.create()
                                .execute(() -> {
                                    // создание базовой сущности
                                    this.createMainEntity();

                                    // создание действия
                                    this.createAction();

                                    // сущности для создания\редакирования
                                    this.createPersistenceObjects();

                                    persistenceObjects
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

                                }).catchException(e -> this.setErrMsg(String.format("%s: %s",
                                this.getClass().getSimpleName(),
                                NullSafe.getErrorMessage(e))))
                                .finallyBlock(() -> {
                                    // обновление действия
                                    this.updateAction();
                                    this.updateMainEntity();
                                });

                    });

            if (NullSafe.notNull(this.getErrMsg())) {

                throw new ActionExecutionException(this.getErrMsg());
            }
            this.afterCommit();
        }
    }

//==========================================================================
    private void createMainEntity() {

        if (this.getEntity().justCreated()) {
            persistanceEntityManager.getEntityManager().persist(this.getEntity());
        }
    }

    //==========================================================================
    private void updateMainEntity() {
        final AbstractPersistenceEntity obj = (AbstractPersistenceEntity) this.getEntity();

        obj.setLast_modify(this.getPersistAction().getExecuteDate());

        persistanceEntityManager.getEntityManager().persist(obj);

    }

    //==========================================================================
    private void createAction() {
        persistanceEntityManager.<AbstractPersistenceAction>createPersistenceEntity(
                AbstractPersistenceAction.class,
                (action) -> {
                    action.setEntity(this.getEntity());
                    action.setActionCode(this.getActionCode());
                    this.setPersistAction(action);
                    action.setActionDuration(LocalTime.MIN);
//                    action.setErrMsg(this.getErrMsg());
                });
    }

    //==========================================================================
    private void updateAction() {
        this.getPersistAction().setActionDuration(LocalTime.MIN.plus(this.stopWatcher.getTimeExecMillis(), ChronoUnit.MILLIS));
        this.getPersistAction().setErrMsg(this.getErrMsg());
        persistanceEntityManager
                .getEntityManager()
                .merge(this.getPersistAction());
    }

    //==========================================================================
    protected void createPersistenceObjects() {

    }

    protected void afterCommit() {

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
