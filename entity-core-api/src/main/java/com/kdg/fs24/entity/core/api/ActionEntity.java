/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.core.api;

import com.kdg.fs24.persistence.api.PersistenceEntity;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.test.api.TestResult;


/**
 *
 * @author kazyra_d
 */
public interface ActionEntity<T extends Action> extends PersistenceEntity {

    Long getEntity_id();

    void doInitialTest();

    Action execEntityAction(
            Integer execActRefID,
            Boolean silentExec,
            ActionEntity oldEntity,
            Long user_id,
            String user_ip
    );

    void saveEntityInstance();

    Boolean getIsAuthorized();

    void setIsAuthorized(Boolean authorized);

    EntityWarningsList getWarningsList(Boolean recreateWarnig);

    Boolean getJustCreated();

    EntityStatus getEntityStatus();

    void postConstruct();
    
    TestResult getTestResult();
    
     void refreshEntity();


}
