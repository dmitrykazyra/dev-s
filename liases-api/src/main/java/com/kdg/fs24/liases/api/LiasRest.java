/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.liases.api;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface LiasRest {

    LocalDate getRest_date();

    BigDecimal getRest();
    
    void setRest(BigDecimal rest);
}
