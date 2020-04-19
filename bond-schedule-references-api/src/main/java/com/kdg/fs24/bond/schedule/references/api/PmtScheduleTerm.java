/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.bond.schedule.references.api;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
public class PmtScheduleTerm extends AbstractRefRecord implements ReferenceRec {

    private Integer pmt_term_id;
    private String pmt_term_name;
    private Boolean is_actual;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - {%s}", this.getPmt_term_id(), this.getPmt_term_name()), this.getPmt_term_id());
    }
}
