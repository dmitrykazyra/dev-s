/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.tariff;

import com.kdg.fs24.application.core.api.ObjectRoot;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

/**
 *
 * @author kazyra_d
 */
public class TariffRate_3 { //implements TariffRateRecord {

    // тариф c фиксированной суммой
    private LocalDate rate_date;
    private BigDecimal rate_value;
    private Integer currency_id;
    private BigDecimal fix_sum;

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
}
