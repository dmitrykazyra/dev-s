/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.entity.core.api.EntityClassesPackages;
import lombok.Data;
import org.springframework.stereotype.Service;
import com.kdg.fs24.counterparties.api.Counterparty;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.entity.status.EntityStatusPK;
import java.time.LocalDateTime;

/**
 *
 * @author N76VB
 */
@Data
@Service
@EntityClassesPackages(pkgList = {"com.kdg.fs24.counterparties.api"})
public class CounterpartyActionsService extends ActionExecutionService {

    public Counterparty createCounterparty(final String counterpartyCode,
            final String shortName,
            final String fullName) {

        return this.<Counterparty>createActionEntity(Counterparty.class,
                (counterparty) -> {
                    counterparty.setCounterpartyCode(counterpartyCode);
                    counterparty.setCreation_date(LocalDateTime.now());
                    counterparty.setShortName(shortName);
                    counterparty.setFullName(fullName);
                    
                    final EntityStatusPK entityStatusPK = NullSafe.createObject(EntityStatusPK.class);

                    entityStatusPK.setEntityStatusId(1);
                    entityStatusPK.setEntityTypeId(200);

                    final EntityStatus userStatus = this.getPersistanceEntityManager()
                            .getEntityManager()
                            .find(EntityStatus.class, entityStatusPK);

                    counterparty.setEntityStatus(userStatus);                    
                });
    }
}
