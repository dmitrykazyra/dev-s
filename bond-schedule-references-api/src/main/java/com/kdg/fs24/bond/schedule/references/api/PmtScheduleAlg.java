/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.bond.schedule.references.api;

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
@Table(name = "core_pmtScheduleAlgsRef")
public class PmtScheduleAlg extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "schedule_alg_id")
    private Integer scheduleAlgId;
    @Column(name = "schedule_alg_name")
    private String scheduleAlgName;
    @Column(name = "is_actual")
    private Boolean isActual;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(this.toString(), this.getScheduleAlgId());
    }
}
