/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.tariff;

import java.io.Serializable;
import lombok.Data;
import java.time.LocalDate;
import com.kdg.fs24.references.application.currency.Currency;

/**
 *
 * @author N76VB
 */
@Data
public class TariffRate_1PK implements Serializable {

    private TariffRate tariffRate;
    private LocalDate rateDate;
    private Currency currency;

}
