package com.kdg.fs24.entity.core;

import com.kdg.fs24.entity.core.api.Action;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import java.util.Collection;
import javax.persistence.Transient;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
public abstract class AbstractAction<T extends ActionEntity>
        extends AbstractPersistenceAction<T> {

    // объекты для персистенса
    @Transient
    private Collection<PersistenceEntity> persistenceObjects
            = ServiceFuncs.<PersistenceEntity>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

    public void execute() {
        if (this.isValid()) {

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
