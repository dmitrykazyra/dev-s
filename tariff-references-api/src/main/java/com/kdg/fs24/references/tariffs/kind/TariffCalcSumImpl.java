/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author kazyra_d
 */
public final class TariffCalcSumImpl implements TariffCalcSum {

    private LocalDate tariff_calc_date;
    private BigDecimal accrualBasis;
    private BigDecimal percRate;
    private BigDecimal tariff_sum;
    private BigDecimal tax_sum;

    @Override
    public LocalDate getTariff_calc_date() {
        return tariff_calc_date;
    }

    public TariffCalcSumImpl setTariff_calc_date(final LocalDate tariff_calc_date) {
        this.tariff_calc_date = tariff_calc_date;
        return this;
    }

    @Override
    public BigDecimal getAccrualBasis() {
        return accrualBasis;
    }

    public TariffCalcSumImpl setAccrualBasis(final BigDecimal accrualBasis) {
        this.accrualBasis = accrualBasis;
        return this;
    }

    @Override
    public BigDecimal getPercRate() {
        return percRate;
    }

    public TariffCalcSumImpl setPercRate(final BigDecimal percRate) {
        this.percRate = percRate;
        return this;
    }

    @Override
    public BigDecimal getTariff_sum() {
        return tariff_sum;
    }

    public TariffCalcSumImpl setTariff_sum(final BigDecimal tariff_sum) {
        this.tariff_sum = tariff_sum;
        return this;
    }
    
    @Override
    public TariffCalcSumImpl incTariff_sum(final BigDecimal tariff_sum) {
        this.tariff_sum.add(tariff_sum);
        return this;
    }

    @Override
    public BigDecimal getTax_sum() {
        return tax_sum;
    }

    public TariffCalcSumImpl setTax_sum(final BigDecimal tax_sum) {
        this.tax_sum = tax_sum;
        return this;
    }

}
