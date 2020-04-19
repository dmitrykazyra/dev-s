/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

/**
 *
 * @author N76VB
 */
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Collection;
//import com.kdg.fs24.application.core.api.ObjectRoot;

public final class TariffRate_1 implements TariffRateRecord {

    // одноставочный тариф 
    private LocalDate rate_date;
    private BigDecimal rate_value;
    private Integer currency_id;

    //==========================================================================
    public static void store(final Collection<TariffRate_1> collection, final Integer rate_id) {
        // сохранение тарифицируемых услуг на плане
//        ObjectRoot.getStaticDbService()
//                .createCallBath("{call tariff_insertorupdate_tariff_rates_1(:RATE, :DATE, :CURR, :PERC)}")
//                .execBatch(stmt -> {
//
//                    collection
//                            .stream()
//                            .forEach((tariffRate_1) -> {
//                                stmt.setParamByName("RATE", rate_id);
//                                stmt.setParamByName("DATE", tariffRate_1.getRate_date());
//                                stmt.setParamByName("CURR", tariffRate_1.getCurrency_id());
//                                stmt.setParamByName("PERC", tariffRate_1.getRate_value());
//                                stmt.addBatch();
//
//                                //tariffKind.getTariffRates().
//                            });
//                });
    }

    //==========================================================================
    public TariffRate_1(
            final Integer currency_id,
            final LocalDate rate_date,
            final BigDecimal rate_value) {

        this.setCurrency_id(currency_id);
        this.setRate_date(rate_date);
        this.setRate_value(rate_value);

    }

    //==========================================================================
    @Override
    public LocalDate getRate_date() {
        return rate_date;
    }

    @Override
    public BigDecimal getRate_value() {
        return rate_value;
    }

    public TariffRate_1 setRate_date(final LocalDate rate_date) {
        this.rate_date = rate_date;
        return this;
    }

    public TariffRate_1 setRate_value(final BigDecimal rate_value) {
        this.rate_value = rate_value;
        return this;
    }

    public Integer getCurrency_id() {
        return currency_id;
    }

    public TariffRate_1 setCurrency_id(final Integer currency_id) {
        this.currency_id = currency_id;
        return this;
    }

}
