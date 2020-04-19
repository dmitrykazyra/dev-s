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
import java.util.Collection;
import java.util.List;

// перечень обязательств по договору
public interface ContractDebts<T> {

    //private List<LiasDebt>  debtsList;
    //void update(List<NewLiasOper> opers);
    void update(final Collection<NewLiasOper> opers);

    void store();

   // void load(Long entity_id, Integer counterparty_id);
    Collection<T> getContactDebts();

    //List<LiasDebt> getContactDebts();

   // void setContactDebts(List<LiasDebt> contactDebts);

}
