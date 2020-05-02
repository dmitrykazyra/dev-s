/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.debtstate;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "liasDebtStatesRef")
public class LiasDebtState extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "debt_state_id", updatable = false)
    private Integer debtStateId;
    @Column(name = "debt_state_name")
    private String debtStateName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getDebtStateId(), this.getDebtStateName()), this.getDebtStateId());
    }
}
