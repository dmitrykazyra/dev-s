/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.debtstate;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class LiasDebtState extends AbstractRefRecord implements ReferenceRec {

    private Integer debt_state_id;
    private String debt_state_name;

    public LiasDebtState() {
        super();
    }

    public Integer getDebt_state_id() {
        return debt_state_id;
    }

    public LiasDebtState setDebt_state_id(final Integer debt_state_id) {
        this.debt_state_id = debt_state_id;
        return this;
    }

    public String getDebt_state_name() {
        return debt_state_name;
    }

    public LiasDebtState setDebt_state_name(final String debt_state_name) {
        this.debt_state_name = debt_state_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getDebt_state_id(), this.getDebt_state_name()), this.getDebt_state_id());
    }
}
