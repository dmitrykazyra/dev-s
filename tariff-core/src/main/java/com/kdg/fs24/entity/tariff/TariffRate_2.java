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
 * @author N76VB
 */
@Deprecated
public final class TariffRate_2 { //implements TariffRateRecord {

    // диапазонный тариф
    private LocalDate rate_date;
    private BigDecimal rate_value;
    private Integer currency_id;
    private BigDecimal min_sum;
    private BigDecimal max_sum;

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


}
