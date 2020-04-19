/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.serv;

import com.kdg.fs24.references.api.AbstractRefRecord;
import com.kdg.fs24.references.api.ReferenceRec;
import java.util.Map;

/**
 *
 * @author kazyra_d
 */
public abstract class TariffServAbs extends AbstractRefRecord implements TariffServ, ReferenceRec  {

    private Integer tariff_serv_id;
    private Integer tariff_group_id;
    private String tariff_serv_name;
    private Boolean client_pay;

    public TariffServAbs() {
        super();
    }

    //==========================================================================
    public Integer getTariff_serv_id() {
        return tariff_serv_id;
    }

    public TariffServAbs setTariff_serv_id(final Integer tariff_serv_id) {
        this.tariff_serv_id = tariff_serv_id;
        return this;
    }

    public Integer getTariff_group_id() {
        return tariff_group_id;
    }

    public TariffServAbs setTariff_group_id(final Integer tariff_group_id) {
        this.tariff_group_id = tariff_group_id;
        return this;
    }

    public String getTariff_serv_name() {
        return tariff_serv_name;
    }

    public TariffServAbs setTariff_serv_name(final String tariff_serv_name) {
        this.tariff_serv_name = tariff_serv_name;
        return this;
    }

    public Boolean getClient_pay() {
        return client_pay;
    }

    public TariffServAbs setClient_pay(final Boolean client_pay) {
        this.client_pay = client_pay;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.tariff_serv_id, this.tariff_serv_name), this.getTariff_serv_id());
    }

}
