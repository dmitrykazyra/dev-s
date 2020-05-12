/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.contracts.actions;

import com.kdg.fs24.entity.contracts.AbstractEntityServiceContract;
import com.kdg.fs24.entity.core.AbstractAction;

/**
 *
 * @author N76VB
 */
public abstract class AbstractContractAction<T extends AbstractEntityServiceContract> extends AbstractAction<T> {

    @Override
    protected void doCalculation() {
        super.doCalculation();
    }
    //==========================================================================
    public T getContractEntity() {
        return (T) (this.getEntity());
    }
    //==========================================================================
    
//    public static Boolean isAllowed(final EntityContract entity) {
//
//        return entity.getIsAuthorized();
//    }    

//    //==========================================================================
//    @Override
//    protected void beforeUpdate() throws InternalAppException {
//        super.beforeUpdate();
//    }
//
//    //==========================================================================
//    @Override
//    protected void doUpdate() throws Throwable  {
//        super.doUpdate();
//        this.getEntity().saveEntityInstance();
//    }
}
