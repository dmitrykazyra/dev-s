/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.application.currency;

import com.kdg.fs24.references.api.ReferenceRec;
import java.util.Map;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class Currency extends AbstractRefRecord implements ReferenceRec {

    private Integer currency_id;
    private String currency_iso;
    private String currency_name;

    public Currency() {
        super();
    }

    public Integer getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(final Integer currency_id) {
        this.currency_id = currency_id;
    }

    public String getCurrency_iso() {
        return currency_iso;
    }

    public void setCurrency_iso(final String currency_iso) {
        this.currency_iso = currency_iso;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(final String currency_name) {
        this.currency_name = currency_name;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getCurrency_id(), this.getCurrency_name()), this.getCurrency_id());
    }
}
