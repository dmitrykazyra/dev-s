/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

/**
 *
 * @author kazyra_d
 */
import java.time.LocalDate;

public final class TariffAccretionHisory {

    private LocalDate accretion_date;
    private Integer tariff_serv_id;
    private Integer tariff_kind_id;
    private Long contract_id;
    private Long lias_action_id;

    public TariffAccretionHisory() {
        super();
    }
    
    public LocalDate getAccretion_date() {
        return accretion_date;
    }

    public TariffAccretionHisory setAccretion_date(final LocalDate accretion_date) {
        this.accretion_date = accretion_date;
        return this;
    }

    public Integer getTariff_serv_id() {
        return tariff_serv_id;
    }

    public TariffAccretionHisory setTariff_serv_id(final Integer tariff_serv_id) {
        this.tariff_serv_id = tariff_serv_id;
        return this;
    }

    public Integer getTariff_kind_id() {
        return tariff_kind_id;
    }

    public TariffAccretionHisory setTariff_kind_id(final Integer tariff_kind_id) {
        this.tariff_kind_id = tariff_kind_id;
        return this;
    }

    public Long getContract_id() {
        return contract_id;
    }

    public TariffAccretionHisory setContract_id(final Long contract_id) {
        this.contract_id = contract_id;
        return this;
    }

    public Long getLias_action_id() {
        return lias_action_id;
    }

    public TariffAccretionHisory setLias_action_id(final Long lias_action_id) {
        this.lias_action_id = lias_action_id;
        return this;
    }
    
}
