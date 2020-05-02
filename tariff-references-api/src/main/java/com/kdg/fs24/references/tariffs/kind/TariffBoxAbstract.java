/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

import com.kdg.fs24.application.core.service.funcs.CustomCollectionImpl;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.liases.api.LiasDebtRest;
import com.kdg.fs24.entity.liases.api.LiasAction;
import com.kdg.fs24.application.core.locale.NLS;
import com.kdg.fs24.application.core.log.LogService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import com.kdg.fs24.application.core.nullsafe.NullSafe;

/**
 *
 * @author N76VB
 */
public abstract class TariffBoxAbstract implements TariffBox {

    private final Collection<TariffCalcSum> tariffSums;
    protected final Comparator<LiasDebtRest> RDC = (LiasDebtRest rd1, LiasDebtRest rd2) -> rd1.getRestDate().compareTo(rd2.getRestDate());
    protected final Comparator<LiasAction> LAC = (LiasAction la1, LiasAction la2) -> la1.getLiasDate().compareTo(la2.getLiasDate());
    protected final Comparator<TariffRateRecord> TRRC = (TariffRateRecord rd1, TariffRateRecord rd2) -> rd1.getRate_date().compareTo(rd2.getRate_date());

    TariffBoxAbstract() {
        tariffSums = ServiceFuncs.<TariffCalcSum>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
    }

    @Override
    public void printCalculations(final TariffKind tariffKind) {

        //анонимный класс для принтования списка остатков
        final CustomCollectionImpl customCollection = NullSafe.createObject(CustomCollectionImpl.class,
                String.format("%s:: Collection<TariffCalcSum> (%d records)\n",
                        tariffKind.getClass().getCanonicalName(),
                        tariffSums.size()));

        tariffSums.stream()
                .sorted((r1, r2) -> {
                    // сортировка по номеру операции в списке
                    return r1.getTariff_calc_date().compareTo(r2.getTariff_calc_date());
                })
                .forEach((tsc) -> {
                    customCollection.addCustomRecord(() -> String.format("tariff_calc_date: %s; accrual_basis: %s; tariff_perc_rate: %s; tariff_sum: %f, tax_sum: %f\n",
                            NLS.getStringDate(tsc.getTariff_calc_date()),
                            tsc.getAccrualBasis(),
                            tsc.getPercRate(),
                            tsc.getTariff_sum(),
                            tsc.getTax_sum()));
                });
        LogService.LogInfo(this.getClass(), () -> customCollection.getRecord());
    }
    //==========================================================================

    @Override
    public Collection<TariffCalcSum> getTariffSums() {
        return tariffSums;
    }

    //==========================================================================
    protected void addTariffSum(
            final LocalDate calc_date,
            final BigDecimal accrualBasis,
            final BigDecimal accrualRate,
            final BigDecimal accrualSum,
            final BigDecimal taxSum) {

        tariffSums.add(new TariffCalcSumImpl()
                .setTariff_calc_date(calc_date)
                .setAccrualBasis(accrualBasis)
                .setPercRate(accrualRate)
                .setTariff_sum(accrualSum)
                .setTax_sum(taxSum));
    }

    //==========================================================================
    protected void addOrReplaceTariffSum(
            final LocalDate calc_date,
            final BigDecimal accrualBasis,
            final BigDecimal accrualRate,
            final BigDecimal accrualSum,
            final BigDecimal taxSum) {

        NullSafe.create(ServiceFuncs.<TariffCalcSum>getCollectionElement_silent(this.tariffSums,
                ts -> ts.getTariff_calc_date().equals(calc_date)))
                .whenIsNull(() -> {

                    tariffSums.add(new TariffCalcSumImpl()
                            .setTariff_calc_date(calc_date)
                            .setAccrualBasis(accrualBasis)
                            .setPercRate(accrualRate)
                            .setTariff_sum(accrualSum)
                            .setTax_sum(taxSum)
                    );
                })
                .safeExecute((ns_TariffSumm) -> {
                    ((TariffCalcSum) ns_TariffSumm).incTariff_sum(accrualSum);
                });
    }

}
