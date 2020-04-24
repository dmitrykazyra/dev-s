package com.kdg.fs24.entity.core;

import com.kdg.fs24.entity.core.api.Action;
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
                        persistenceObjects.add(this.getEntity());
                        this.doUpdate();

//                        if (persistenceObjects.isEmpty()) {
//                            throw new RuntimeException("There are no objects for persistence");
//                        }
                        NullSafe.create()
                                .execute(() -> {

                                    persistenceObjects
                                            .forEach((obj) -> {

//                                    if (obj instanceof AbstractPersistenceEntity) {
                                                final AbstractPersistenceEntity apeObj = (AbstractPersistenceEntity) (obj);

                                                apeObj.setLast_modify(this.getExecuteDate());
                                                final Boolean needMerge = !apeObj.getJustCreated();
                                                //}

                                                if (needMerge) {
                                                    em.merge(obj);
                                                } else {
                                                    em.persist(obj);
                                                }
                                            });
                                }).catchException(e -> this.setErrMsg(NullSafe.getErrorMessage(e)))
                                .finallyBlock(() -> {
                                    persistanceEntityManager.<AbstractPersistenceAction>createPersistenceEntity(
                                            AbstractPersistenceAction.class,
                                            (action) -> {
                                                action.setEntity(this.getEntity());
                                                action.setActionCode(this.getActionCode());
                                                action.setActionDuration(LocalTime.MIN.plus(this.stopWatcher.getTimeExecMillis(), ChronoUnit.MILLIS));
                                                action.setErrMsg(this.getErrMsg());
                                            });
                                });
                    });

            this.afterCommit();
        }
    }

    protected void doUpdate() {

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
