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
import com.kdg.fs24.lias.opers.napi.NewLiasOper;
import com.kdg.fs24.lias.opers.napi.SaveAccretionHist;
import com.kdg.fs24.liases.exception.LiasRestIsNegative;
import java.math.BigDecimal;
import java.util.Collection;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Lias {

    Integer getLiasId();

    LocalDate getStart_date();

    LocalDate getAllow_date();

    LocalDate getFinal_date();

    LocalDate getLegal_date();

    LocalDateTime getServer_date();

    LocalDate getInactive_date();

    Boolean getIs_Cancelled();

    void store(Integer debt_id);

    Collection<LiasAction> getLiasActions();

    Collection<LiasRest> getContactLiasRests();

    void createLiasOper(NewLiasOper liasOperInfo) throws LiasRestIsNegative;

    void createOrUpdateLiasRests(NewLiasOper liasOperInfo) throws LiasRestIsNegative;

    void createLiasOper(BigDecimal liasSum,
            LocalDate operDate,
            Integer liasFinOperCode,
            Integer liasTypeID,
            int liasOperHC,
            SaveAccretionHist sah);
}
