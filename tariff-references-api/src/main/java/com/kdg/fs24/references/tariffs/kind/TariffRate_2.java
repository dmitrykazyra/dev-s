/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

import com.kdg.fs24.application.core.api.ObjectRoot;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

/**
 *
 * @author N76VB
 */
public final class TariffRate_2 implements TariffRateRecord {

    // диапазонный тариф
    private LocalDate rate_date;
    private BigDecimal rate_value;
    private Integer currency_id;
    private BigDecimal min_sum;
    private BigDecimal max_sum;

    public TariffRate_2(final Integer currency_id, final LocalDate rate_date, final BigDecimal rate_value, final BigDecimal min_sum, final BigDecimal max_sum) {

        this.setRate_date(rate_date);
        this.setRate_value(rate_value);
        this.setCurrency_id(currency_id);
        this.setMin_sum(min_sum);
        this.setMax_sum(max_sum);

    }
    //==========================================================================

//    public static void store(final Collection<TariffRate_2> collection, final Integer rate_id) {
//        // сохранение тарифицируемых услуг на плане
//        ObjectRoot.getStaticDbService()
//                .createCallBath("{call tariff_insertorupdate_tariff_rates_2(:RATE, :DATE, :CURR, :PERC, :MIN, :MAX)}")
//                .execBatch(stmt -> {
//
//                    collection
//                            .stream()
//                            .forEach((tariffRate_1) -> {
//                                stmt.setParamByName("RATE", rate_id);
//                                stmt.setParamByName("DATE", tariffRate_1.getRate_date());
//                                stmt.setParamByName("CURR", tariffRate_1.getCurrency_id());
//                                stmt.setParamByName("PERC", tariffRate_1.getRate_value());
//                                stmt.setParamByName("MIN", tariffRate_1.getMin_sum());
//                                stmt.setParamByName("MAX", tariffRate_1.getMax_sum());
//                                stmt.addBatch();
//
//                                //tariffKind.getTariffRates().
//                            });
//                });
//    }

    @Override
    public LocalDate getRate_date() {
        return rate_date;
    }

    public void setRate_date(final LocalDate rate_date) {
        this.rate_date = rate_date;
    }

    @Override
    public BigDecimal getRate_value() {
        return rate_value;
    }

    public void setRate_value(final BigDecimal rate_value) {
        this.rate_value = rate_value;
    }

    public Integer getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(final Integer currency_id) {
        this.currency_id = currency_id;
    }

    public BigDecimal getMin_sum() {
        return min_sum;
    }

    public void setMin_sum(final BigDecimal min_sum) {
        this.min_sum = min_sum;
    }

    public BigDecimal getMax_sum() {
        return max_sum;
    }

    public void setMax_sum(final BigDecimal max_sum) {
        this.max_sum = max_sum;
    }

}
