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
public interface TariffRateRecord {

    LocalDate getRate_date();

    BigDecimal getRate_value();

}
