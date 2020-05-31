/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.entity.core.api.EntityClassesPackages;
import com.kdg.fs24.entity.kind.EntityKind;
import lombok.Data;
import org.springframework.stereotype.Service;
import com.kdg.fs24.entity.tariff.AbstractTariffPlan;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.kdg.fs24.references.tariffs.api.TariffConst;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author N76VB
 */
@Data
@Service
@EntityClassesPackages(pkgList = {"com.kdg.fs24.entity.tariff"})
public class TariffCoreActionsService extends ActionExecutionService {

    public AbstractTariffPlan createTariffPlan(final String tariffPlanName,
            final String tariffPlanCode,
            final EntityKind entityKind,
            final LocalDate actualDate,
            final LocalDate finishDate) {

        return this.<AbstractTariffPlan>createActionEntity(AbstractTariffPlan.class,
                (tariffPlan) -> {
                    tariffPlan.setPlanKind(entityKind);
                    tariffPlan.setTariffPlanCode(tariffPlanCode);
                    tariffPlan.setTariffPlanName(tariffPlanName);
                    tariffPlan.setActualDate(actualDate);
                    tariffPlan.setFinishDate(finishDate);
                    tariffPlan.setCreation_date(LocalDateTime.now());
                    tariffPlan.setEntityStatus(AbstractRefRecord.<EntityStatus>getRefeenceRecord(
                            EntityStatus.class,
                            record -> record.getEntityStatusId().equals(0)
                            && record.getEntityTypeId().equals(TariffConst.ENTITY_TARIFF_PLAN)));
                });
    }
    //==========================================================================
    
}
