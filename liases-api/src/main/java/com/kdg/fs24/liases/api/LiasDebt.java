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
import com.kdg.fs24.liases.exception.LiasDoesNotExists;
import com.kdg.fs24.liases.exception.LiasRestIsNegative;
import java.util.Collection;
import com.kdg.fs24.references.application.currency.Currency;
import com.kdg.fs24.references.liases.debtstate.LiasDebtState;
import com.kdg.fs24.references.liases.kind.LiasKind;
import com.kdg.fs24.references.liases.type.LiasType;
import com.kdg.fs24.references.liases.baseassettype.LiasBaseAssetType;
import java.time.LocalDate;

public interface LiasDebt<L extends Lias, LDR extends LiasDebtRest> {

    Integer getRowNum();

    Integer getDebtId();

    Currency getCurrency();

    LiasDebtState getLiasDebtState();

    LiasKind getLiasKind();

    LiasType getLiasType();

    LiasBaseAssetType getLiasBaseAssetType();

    LocalDate getDebtStartDate();

    LocalDate getDebtFinalDate();

    Collection<L> getLiases();

    Collection<LDR> getContactDebtRests();
    
    Collection<LiasAction> getLiasActions(LocalDate D1, LocalDate D2);

    //Counterparty getCounterparty();
    //EntityContract getEntityContract();
    void store(Long entity_id, Long counterparty_id);

    void createOrUpdateLiases(NewLiasOper liasOperInfo) throws LiasDoesNotExists, LiasRestIsNegative;

    void createOrUpdateDebtRests(NewLiasOper liasOperInfo);

    Lias findLias(
            LocalDate startDate,
            LocalDate finalDate,
            Boolean throwExcWhenNotFound) throws LiasDoesNotExists;

    Lias findFirstLias() throws LiasDoesNotExists;
    
    void printLiasDebtRests();

}
