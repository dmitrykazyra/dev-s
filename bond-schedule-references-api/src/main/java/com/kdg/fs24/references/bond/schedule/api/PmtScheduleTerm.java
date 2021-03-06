/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.bond.schedule.api;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "core_pmtScheduleTermsRef")
public class PmtScheduleTerm extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "pmt_term_id")
    private Integer pmtTermId;
    @Column(name = "pmt_term_name")
    private String pmtTermName;
    @Column(name = "is_actual")
    private Boolean isActual;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(this.toString(), this.getPmtTermId());
    }

    //==========================================================================
    public final static PmtScheduleTerm findPmtScheduleTerm(final Integer PmtScheduleTermId) {
        return AbstractRefRecord.<PmtScheduleTerm>getRefeenceRecord(PmtScheduleTerm.class,
                record -> record.getPmtTermId().equals(PmtScheduleTermId));
    }
}
