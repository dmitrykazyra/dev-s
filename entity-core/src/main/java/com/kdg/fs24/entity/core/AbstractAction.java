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
import javax.persistence.Transient;
import com.kdg.fs24.tce.api.StopWatcher;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
public abstract class AbstractAction<T extends ActionEntity>
        extends AbstractPersistenceAction<T> {

    // объекты для персистенса
    private PersistanceEntityManager persistanceEntityManager;

    private Collection<PersistenceEntity> persistenceObjects
            = ServiceFuncs.<PersistenceEntity>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

    private StopWatcher stopWatcher;

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
                        this.doUpdate();
                        persistenceObjects
                                .forEach((obj) -> {
                                    em.persist(obj);
                                    //em.refresh(entity);
                                });
                        //em.flush();
                    });

            //final AbstractPersistenceAction<T> ent2persist = this;
            final AbstractPersistenceAction<T> ent2persist = NullSafe.createObject(AbstractPersistenceAction.class);
            ent2persist.setEntity(this.getEntity());
            ent2persist.setActionCode(this.getActionCode());
            ent2persist.setActionDuration(LocalTime.MIN.plus(this.stopWatcher.getTimeExecMillis(), ChronoUnit.MILLIS));
            //      this.setActionDuration(LocalTime.MIN.plus(this.stopWatcher.getTimeExecMillis(), ChronoUnit.MILLIS));

            // сохранили действие
            persistanceEntityManager
                    .executeTransaction(em -> {
                        em.persist(ent2persist);
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
