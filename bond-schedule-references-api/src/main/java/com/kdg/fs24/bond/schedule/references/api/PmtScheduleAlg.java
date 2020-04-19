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
public class PmtScheduleAlg extends AbstractRefRecord implements ReferenceRec {


    private Integer schedule_alg_id;
    private String schedule_alg_name;
    private Boolean is_actual;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - {%s}", this.getSchedule_alg_id(), this.getSchedule_alg_name()), this.getSchedule_alg_id());
    }
}
