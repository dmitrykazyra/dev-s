/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import com.kdg.fs24.spring.core.api.ApplicationRepositoryService;
import org.springframework.stereotype.Service;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import com.kdg.fs24.entity.type.EntityType;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.entity.action.ActionCode;
import javax.persistence.Column;

/**
 *
 * @author N76VB
 */
@Data
@Service
public class EntityReferencesService implements ApplicationRepositoryService {

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    //==========================================================================
    public EntityType createNewEntityType(final Integer entityTypeId,
            final String entityTypeName,
            final String entityAppName) {

        final EntityType entityType = NullSafe.createObject(EntityType.class);

        entityType.setEntityAppName(entityAppName);
        entityType.setEntityTypeId(entityTypeId);
        entityType.setEntityTypeName(entityTypeName);

        return entityType;

    }

    //==========================================================================
    public EntityStatus createNewEntityStatus(final Integer entityStatusId,
            final Integer entityTypeId,
            final String entityStatusName) {

        final EntityStatus entityStatus = NullSafe.createObject(EntityStatus.class);

        entityStatus.setEntityStatusId(entityStatusId);
        entityStatus.setEntityStatusName(entityStatusName);
        entityStatus.setEntityTypeId(entityTypeId);

        return entityStatus;
    }
    //==========================================================================

    public ActionCode createNewActionCode(final Integer actionCode,
            final String actionName,
            final String appName,
            final Boolean isClosed) {
       
        
        final ActionCode newActionCode = NullSafe.createObject(ActionCode.class);

        newActionCode.setActionCode(actionCode);
        newActionCode.setActionName(actionName);
        newActionCode.setAppName(appName);
        newActionCode.setIsClosed(isClosed);

        return newActionCode;
    }
}
