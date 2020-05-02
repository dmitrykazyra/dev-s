/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.kind;

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
@Table(name = "liasKindsRef")
public class LiasKind extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "lias_kind_id")
    private Integer liasKindId;
    @Column(name = "is_claim")
    private Boolean isClaim;
    @Column(name = "lias_group_id")
    private Integer liasGroupId;
    @Column(name = "lias_kind_name")
    private String liasKindName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getLiasKindId(), this.toString()), this.getLiasKindId());
    }
}
