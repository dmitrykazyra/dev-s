/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.liases.api;

/**
 *
 * @author kazyra_d
 */
import java.time.LocalDate;
import java.math.BigDecimal;

public interface LiasDebtRest {

    LocalDate getRest_date();

    BigDecimal getRest();
    
    void setRest(BigDecimal rest);
    
    void incRest(BigDecimal rest);

}