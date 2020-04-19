/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

import java.util.Collection;

/**
 *
 * @author kazyra_d
 */
public interface TariffRate<T> {

    Integer getServ_id();

    void setServ_id(Integer serv_id);

    Integer getRate_id();

    void setRate_id(Integer rate_id);

    String getRate_name();

    Integer getTariff_scheme_id();

    Collection<T> getRateRecords();

    <TR extends TariffRateRecord> TariffRate<TR> addRateRecord(final TR rateRecord);

    void store();

    void printRates();
    //String getCallStmtQuery();

    Integer getKind_id();

    void setKind_id(Integer kind_id);
}
