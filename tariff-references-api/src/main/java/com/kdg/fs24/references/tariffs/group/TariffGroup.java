/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.group;


import com.kdg.fs24.references.api.ReferenceRec;
import java.util.Map;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author N76VB
 */
public class TariffGroup  extends AbstractRefRecord implements ReferenceRec {

    private Integer tariff_group_id;
    private String tariff_group_name;

    public TariffGroup() {
        super();
    }

    public TariffGroup(final Integer tariff_group_id, final String tariff_group_name) {
        this();
        this.tariff_group_id = tariff_group_id;
        this.tariff_group_name = tariff_group_name;
    }

    //==========================================================================
    public Integer getTariff_group_id() {
        return tariff_group_id;
    }

    public TariffGroup setTariff_group_id(final Integer tariff_group_id) {
        this.tariff_group_id = tariff_group_id;
        return this;
    }

    public String getTariff_group_name() {
        return tariff_group_name;
    }

    public TariffGroup setTariff_group_name(final String tariff_group_name) {
        this.tariff_group_name = tariff_group_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getTariff_group_id(), this.getTariff_group_name()), this.getTariff_group_id());
    }

}
