/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.contracts.actions;

import com.kdg.fs24.entity.core.api.ActionCodeId;
import com.kdg.fs24.entity.core.api.EntityContractConst;
import com.kdg.fs24.entity.core.api.PreViewDialog;
import com.kdg.fs24.entity.core.api.ViewAction;
import com.kdg.fs24.entity.contracts.AbstractEntityServiceContract;
import com.kdg.fs24.entity.tariff.TariffCalculations;
import lombok.Data;
import java.time.LocalDate;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.tariff.TariffCalcRecord;
import com.kdg.fs24.entity.tariff.TariffRate;
import com.kdg.fs24.references.tariffs.serv.TariffServ;
import com.kdg.fs24.references.tariffs.kind.TariffKind;
import java.util.Collection;
import com.kdg.fs24.entity.tariff.TariffKindService;

/**
 *
 * @author N76VB
 */
@ActionCodeId(action_code = EntityContractConst.ACT_CALCULATE_TARIFFS,
        action_name = "Пересчет тарифицируемых сумм")
@Data
@ViewAction
@PreViewDialog
public class ActCalculateTariffSums<T extends AbstractEntityServiceContract> extends AbstractContractAction<T> {

    //private final TariffCalculations tariffCalculations = NullSafe.createObject(TariffCalculations.class, SysConst.LONG_ZERO);
    private LocalDate D1, D2;
    private Collection<TariffCalcRecord> newTariffCalcRecords = ServiceFuncs.<TariffCalcRecord>createCollection();

    @Override
    protected void doCalculation() {

        if (this.getContractEntity().getTariffPlan().getTariffServs().isEmpty()) {
            throw new RuntimeException("TariffPlan is empty");
        }

        this.D1 = this.getContractEntity().getBeginDate();
        this.D2 = this.getContractEntity().getEndDate();

        // строим калькуляции на основании услуг из тарифного плана
        this.getContractEntity()
                .getTariffPlan()
                .getTariffServs()
                .stream()
                .forEach(serv -> {
                    // тарифицируемая услуга на плане
                    final TariffServ tariffServ = serv.getTariffServ();
                    //serv.getTariffRates()
                    // ищем расчет с тарифицируемой услугой

                    final TariffCalcRecord tariffCalcRecord = NullSafe.createObject(TariffCalcRecord.class);

                    // актуальные ставки
                    final TariffRate tariffRate = serv
                            .getTariffRates()
                            .stream()
                            .filter(rate -> rate.getTariffKind().getTariffServ().getTariffServId().equals(tariffServ.getTariffServId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException(String.format("Rate not found for servId=%d", tariffServ.getTariffServId())));
                    // ищем расчет
                    tariffCalcRecord.setTariffRate(tariffRate);
                    tariffCalcRecord.setEntity(this.getContractEntity());

                    // вид рассчета
                    final TariffKindService tariffKind = (TariffKindService) serv.getTariffKind();
                    // суммы калькуляции за период
                    tariffCalcRecord.setTariffSums(tariffKind.calculateTariff(this.getContractEntity(), tariffRate, D1, D2));

                    newTariffCalcRecords.add(tariffCalcRecord);

                });
    }

    //==========================================================================
//    @Override
//    protected boolean isValid() {
//
//        //return !tariffCalculations.getTariffCalsSums().isEmpty();
//    }
    //==========================================================================
//    public static Boolean isAllowed(final EntityContract entity) {
//
//        return entity.getIsAuthorized();
//        //return true;
//    }
    @Override
    protected void doUpdate() {

        super.doUpdate();
        // синхронизация с основными рассчетами
        
//        this.getEntity()
//                .getTariffCalculations()
//                .store(D1, D2);

    }
}
