/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.application.currency;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.references.api.ReferenceRec;
import java.util.Map;
import com.kdg.fs24.references.api.AbstractRefRecord;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "core_CurrenciesRef")
public class Currency extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "currency_id")
    private Integer currencyId;
    @Column(name = "currency_iso")
    private String currencyIso;
    @Column(name = "currency_name")
    private String currencyName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getCurrencyId(), this.getCurrencyName()), this.getCurrencyId());
    }

    //==========================================================================
    public static Currency getCurrency(final Integer currencyId) {

        return ServiceFuncs.getMapValue(REF_CACHE, mapEntry -> mapEntry.getKey().equals(Currency.class))
                .get()
                .stream()
                .map(x -> (Currency) x)
                .collect(Collectors.toList())
                .stream()
                .filter(currency -> currency.getCurrencyId().equals(currencyId))
                .findFirst()
                .get();
    }
}
