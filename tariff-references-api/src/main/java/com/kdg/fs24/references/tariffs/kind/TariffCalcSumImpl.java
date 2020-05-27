/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
public class TariffCalcSumImpl {

    private LocalDate tariff_calc_date;
    private BigDecimal accrualBasis;
    private BigDecimal percRate;
    private BigDecimal tariff_sum;
    private BigDecimal tax_sum;

}
