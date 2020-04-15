/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.action;

import com.kdg.fs24.exception.references.ReferenceNotFound;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.references.core.AbstractReference;
import com.kdg.fs24.application.core.nullsafe.NullSafe;

/**
 *
 * @author kazyra_d
 */
@Deprecated
public class ActionCodesRef<T extends ActionCode> extends AbstractReference<ActionCode> {

    //==========================================================================
    public String getActionNameById(final Integer action_code) throws ReferenceNotFound {

        return this.getActionCodeById(action_code).getActionName();
    }

    //==========================================================================
    public T getActionCodeById(final Integer action_code) throws ReferenceNotFound {

        return (T) this.<T>findReference(() -> (this.findActionCodeById(action_code)),
                String.format("Неизвестный код действия (ActionCodeRef.action_code=%d)", action_code));
    }

    //==========================================================================
    private T findActionCodeById(final Integer action_code) {

        T ac = null;

        ac = (T) this.findCachedRecords((object) -> ((T) object).getActionCode().equals(action_code));

        if (NullSafe.isNull(ac)) {

            LogService.LogErr(this.getClass(), () -> String.format("%s: %s", LogService.getCurrentObjProcName(this), String.format("action code does not exists (%d) ", action_code)));
            // возможно нет записи в ActionsCodesRef
        }

        //return (ActionCode) this.findCachedRecords((object) -> ((ActionCode) object).getAction_code().equals(action_code));
        return ac;
    }
}
