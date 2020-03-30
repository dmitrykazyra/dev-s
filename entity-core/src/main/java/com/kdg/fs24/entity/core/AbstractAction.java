package com.kdg.fs24.entity.core;

import com.kdg.fs24.entity.core.api.Action;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;


/**
 *
 * @author kazyra_d
 */
public abstract class AbstractAction<T extends ActionEntity>
        extends AbstractPersistenceAction<T>
        implements Action<T> {

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
                .execute2result(() -> (entity.getEntityStatus().getEntity_status_id().equals(allowedStatus))))
                .catchException2result((e) -> SysConst.IS_NOT_ALLOWED_ACTION)
                .<Boolean>getObject();

//        return (entity.getEntityStatus().getEntity_status_id().equals(allowedStatus));
    }

}
