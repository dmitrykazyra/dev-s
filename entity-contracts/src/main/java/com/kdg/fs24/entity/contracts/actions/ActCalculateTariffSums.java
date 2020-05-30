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

    private final TariffCalculations tariffCalculations = NullSafe.createObject(TariffCalculations.class, SysConst.LONG_ZERO);
    private LocalDate D1, D2;

    @Override
    protected void doCalculation() {
//        this.D1 = this.getContractEntity().getBeginDate();
//        //this.D2 = this.getEntity().getBegin_date().plusDays(30);
//        this.D2 = this.getContractEntity().getClose_date();
//        this.tariffCalculations.setEntity_id(this.getEntity().getContract_id());
//
//        final TariffKind tariffKind = this.getEntity()
//                .getTariffPlan()
//                .getTariffKind(TariffConst.TS_MAIN_PERCENTS);
//
////        tariffKind.getTariffSum(this.getEntity().getTariffCalculations(),
////                this.getEntity(), D1, D2);
//        this.getEntity()
//                .getTariffPlan()
//                .getTariffKinds()
//                .stream()
//                .forEach(tk -> {
//
//                    tariffCalculations.add(((TariffKind) tk).getTariffRate(),
//                            ((TariffKind) tk).calculateTariff(
//                                    this.getEntity(), D1, D2));
//                });
//
//        if (!this.tariffCalculations.getTariffCalsSums().isEmpty()) {
//
//            this.getEntity()
//                    .getTariffCalculations()
//                    .merge(this.tariffCalculations, D1, D2);
//        }
//        // печать в лог суммы тарифа
//        final BigDecimal tariffSumm = this.getEntity().getTariffSum(tariffKind, D1, D2);
    }

    //==========================================================================
    @Override
    protected boolean isValid() {

        return !tariffCalculations.getTariffCalsSums().isEmpty();
    }

    //==========================================================================
//    public static Boolean isAllowed(final EntityContract entity) {
//
//        return entity.getIsAuthorized();
//        //return true;
//    }
    @Override
    protected void doUpdate() {

        super.doUpdate();
//        this.getEntity()
//                .getTariffCalculations()
//                .store(D1, D2);

    }
}
