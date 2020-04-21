/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.loan.references.api;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.references.api.ReferenceRec;
import java.util.Map;
import com.kdg.fs24.references.api.AbstractRefRecord;
import static com.kdg.fs24.references.api.AbstractRefRecord.REF_CACHE;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "rlc_loanSourcesRef")
public class LoanSource extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "loan_source_id", updatable = false)
    private Integer loanSourceId;
    @Column(name = "loan_source_name")
    private String loanSourceName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getLoanSourceId(), this.toString()), this.getLoanSourceId());
    }

    public static LoanSource getExistLoanSource(final Integer loanSourceId) {

        return ServiceFuncs.getMapValue(REF_CACHE, mapEntry -> mapEntry.getKey().equals(LoanSource.class))
                .get()
                .stream()
                .map(x -> (LoanSource) x)
                .collect(Collectors.toList())
                .stream()
                .filter(loanSource -> loanSource.getLoanSourceId().equals(loanSourceId))
                .findFirst()
                .get();
    }
}
