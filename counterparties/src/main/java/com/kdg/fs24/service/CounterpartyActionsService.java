/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.entity.core.api.EntityClassesPackages;
import lombok.Data;
import org.springframework.stereotype.Service;
import com.kdg.fs24.entity.counterparties.api.Counterparty;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.references.api.AbstractRefRecord;
import java.time.LocalDateTime;

/**
 *
 * @author N76VB
 */
@Data
@Service
@EntityClassesPackages(pkgList = {"com.kdg.fs24.entity.counterparties.api"})
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
                    counterparty.setEntityStatus(AbstractRefRecord.<EntityStatus>getRefeenceRecord(
                            EntityStatus.class,
                            record -> record.getEntityStatusId().equals(1)
                            && record.getEntityTypeId().equals(200)));                    
                });
    }
}
