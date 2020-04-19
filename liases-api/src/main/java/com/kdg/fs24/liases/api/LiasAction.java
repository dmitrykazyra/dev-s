/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.liases.api;

import com.kdg.fs24.references.liases.actiontype.LiasActionType;
import com.kdg.fs24.references.liases.finopercode.LiasFinOperCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author kazyra_d
 */
public interface LiasAction {

    // код финансовой операции
    LiasFinOperCode getLiasFinOperCode();

    // тип финансовой операции
    LiasActionType getLiasActionType();

    // сумма финансовой операции
    BigDecimal getLias_sum();

    // дата финансовой операции
    LocalDate getLias_date();

    void store(Integer lias_id);

    void setDoc_id(Long doc_id);

    int getLiasOperHC();

}
