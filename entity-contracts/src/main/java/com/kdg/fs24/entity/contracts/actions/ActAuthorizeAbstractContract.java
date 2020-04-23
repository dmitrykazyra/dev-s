/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.contracts.actions;

import com.kdg.fs24.entity.contracts.AbstractEntityContract;
import com.kdg.fs24.entity.core.AbstractAction;
import com.kdg.fs24.entity.core.api.ActionCodeId;
import com.kdg.fs24.entity.core.api.AllowedMethod;
import com.kdg.fs24.entity.core.api.EntityConst;
import com.kdg.fs24.entity.core.api.EntityContractConst;
import com.kdg.fs24.entity.core.api.ViewAction;
import com.kdg.fs24.entity.core.api.PreViewDialog;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
@ActionCodeId(action_code = EntityContractConst.ACT_AUTHORIZE_CONTRACT,
        action_name = "Авторизация договора")
@ViewAction
@PreViewDialog
//@AllowedMethod(action = ActAuthorizeAbstractContract.class, entity = EntityContract.class)
public class ActAuthorizeAbstractContract extends AbstractContractAction<AbstractEntityContract> {

    //==========================================================================
    public static Boolean isAllowed(final AbstractEntityContract entity) {

//        return (NullSafe.create()
//                .execute2result(() -> {
//                    return !entity.getIsAuthorized() && (entity.getEntityStatus().getEntity_status_id().equals(SysConst.ES_VALID));
//                }))
//                .catchException2result((e) -> {
//                    return SysConst.IS_NOT_ALLOWED_ACTION;
//                })
//                .<Boolean>getObject();
        return true;

    }

    //==========================================================================
//    @Override
//    protected void doUpdate() {
//        NullSafe.create()
//                .execute(() -> {
//                    this.getContractEntity()
//                            .getEntityMarks()
//                            .saveEntityMark(
//                                    this.getEntity().getEntity_id(),
//                                    this.actionId,
//                                    EntityConst.MR_AUTHORIZE_ENTITY,
//                                    EntityConst.MR_AUTHORIZE_ENTITY_AUTH);
//                }).throwException();
//    }

    //==========================================================================    
    @Override
    protected final void afterCommit() {
        // добавили отметку в коллекцию отметок
//        this.getContractEntity()
//                .getEntityMarks()
//                .addEntityMark(EntityConst.MR_AUTHORIZE_ENTITY,
//                        EntityConst.MR_AUTHORIZE_ENTITY_NOT_AUTH);
//        this.getContractEntity()
//                .setIsAuthorized(EntityConst.IS_AUTHORIZED);
    }
    //==========================================================================
}