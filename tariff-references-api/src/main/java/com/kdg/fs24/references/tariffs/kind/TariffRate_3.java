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
 * @author kazyra_d
 */
public class TariffRate_3 implements TariffRateRecord {

    // тариф c фиксированной суммой
    private LocalDate rate_date;
    private BigDecimal rate_value;
    private Integer currency_id;
    private BigDecimal fix_sum;

    public TariffRate_3(final Integer currency_id, final LocalDate rate_date, final BigDecimal rate_value, final BigDecimal fix_sum) {

        this.setRate_date(rate_date);
        this.setRate_value(rate_value);
        this.setCurrency_id(currency_id);
        this.setFix_sum(fix_sum);

    }

//    public static void store(final Collection<TariffRate_3> collection, final Integer rate_id) {
//        // сохранение тарифицируемых услуг на плане
//        ObjectRoot.getStaticDbService()
//                .createCallBath("{call tariff_insertorupdate_tariff_rates_3(:RATE, :DATE, :CURR, :PERC, :FS)}")
//                .execBatch(stmt -> {
//
//                    collection
//                            .stream()
//                            .forEach((tariffRate_3) -> {
//                                stmt.setParamByName("RATE", rate_id);
//                                stmt.setParamByName("DATE", tariffRate_3.getRate_date());
//                                stmt.setParamByName("CURR", tariffRate_3.getCurrency_id());
//                                stmt.setParamByName("PERC", tariffRate_3.getRate_value());
//                                stmt.setParamByName("FS", tariffRate_3.getFix_sum());
//                                stmt.addBatch();
//
//                                //tariffKind.getTariffRates().
//                            });
//                });
//    }

    public LocalDate getRate_date() {
        return rate_date;
    }

    public void setRate_date(final LocalDate rate_date) {
        this.rate_date = rate_date;
    }

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

    public BigDecimal getFix_sum() {
        return fix_sum;
    }

    public void setFix_sum(final BigDecimal fix_sum) {
        this.fix_sum = fix_sum;
    }

}
