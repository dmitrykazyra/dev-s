/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.retail.tariff.kind;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.references.tariffs.api.TariffConst;
import com.kdg.fs24.entity.tariff.TariffKindService;
import com.kdg.fs24.references.tariffs.kind.TariffKindId;
import com.kdg.fs24.entity.tariff.TariffRate_1;
import com.kdg.fs24.entity.retail.loan.contracts.AbstractRetailLoanContract;
import com.kdg.fs24.test.api.TestConst;
import com.kdg.fs24.references.serv.TariffServMainPerc;
import java.util.Collection;
import java.time.LocalDate;
import com.kdg.fs24.entity.tariff.TariffCalcSum;
import com.kdg.fs24.entity.debts.LiasDebt;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.references.api.LiasesConst;
import com.kdg.fs24.entity.calculations.TariffRestBox;
import java.math.BigDecimal;
import com.kdg.fs24.entity.tariff.TariffRate;

/**
 *
 * @author N76VB
 */
@TariffKindId(
        //        tariff_serv_class = TariffServCashBack.class,
        tariff_serv_id = TariffConst.TS_MAIN_PERCENTS,
        tariff_kind_id = TariffConst.TK_CURRENT_RESTS,
        tariff_kind_name = "От фактического остатка на счете",
        tariff_scheme_id = TariffConst.SCH_30_365)
public class TariffKindMainPercAtRest extends TariffKindService<TariffServMainPerc, AbstractRetailLoanContract, TariffRate_1, TariffRestBox> {

    @Override
    public Collection<TariffCalcSum> calculateTariff(
            final AbstractRetailLoanContract contract,
            final TariffRate tariffRate,
            final LocalDate D1, final LocalDate D2) {
        if (SysConst.DEBUG_MODE.get()) {
            LogService.LogInfo(this.getClass(), () -> String.format("Calculate tariff for %s(%s) [%s:%s]",
                    contract.getClass().getCanonicalName(),
                    this.getClass().getCanonicalName(),
                    D1, D2));
        }

        // остатки по основному долгу
        final LiasDebt liasDebt = contract
                .getContractDebts()
                .stream()
                .filter(debt -> debt.getLiasKind().getLiasKindId().equals(LiasesConst.LKI_RETURN_MAIN_DEBT))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Can't find debt rests (lias_kind_id = %d)", LiasesConst.LKI_RETURN_MAIN_DEBT)));

        if (SysConst.DEBUG_MODE.get()) {
            liasDebt.printLiasDebtRests();
            tariffRate.printRates();
        }

        final TariffRestBox tariffBoxStd = this.buildTariffBox();

        tariffBoxStd.createCalculations(D1, D2,
                liasDebt.getLiasDebtRests(),
                tariffRate,
                (rateDate, rateBasis, rate) -> {
                    return BigDecimal.valueOf((double) ((rateBasis.doubleValue()) * rate.doubleValue()) / 36000);
                });
        if (SysConst.DEBUG_MODE.get()) {
            // печать остатков
            tariffBoxStd.printCalculations(this);
        }

        //return tariffBoxStd.getTariffSums();
        return null;
    }
}
