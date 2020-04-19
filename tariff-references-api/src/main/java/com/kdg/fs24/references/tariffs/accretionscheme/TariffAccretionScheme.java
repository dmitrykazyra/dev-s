/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.accretionscheme;


import com.kdg.fs24.references.api.ReferenceRec;
import java.util.Map;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class TariffAccretionScheme  extends AbstractRefRecord implements ReferenceRec {

    private Integer tariff_scheme_id;
    private String tariff_scheme_name;

    public TariffAccretionScheme() {
        super();
    }

    public TariffAccretionScheme(final Integer tariff_scheme_id, final String tariff_scheme_name) {
        this();
        this.tariff_scheme_id = tariff_scheme_id;
        this.tariff_scheme_name = tariff_scheme_name;
    }

    //==========================================================================
    public Integer getTariff_scheme_id() {
        return tariff_scheme_id;
    }

    public TariffAccretionScheme setTariff_scheme_id(final Integer tariff_scheme_id) {
        this.tariff_scheme_id = tariff_scheme_id;
        return this;
    }

    public String getTariff_scheme_name() {
        return tariff_scheme_name;
    }

    public TariffAccretionScheme setTariff_scheme_name(final String tariff_scheme_name) {
        this.tariff_scheme_name = tariff_scheme_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getTariff_scheme_id(), this.getTariff_scheme_name()), this.getTariff_scheme_id());
    }

}