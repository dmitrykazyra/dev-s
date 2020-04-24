/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.marks;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Entity
@Data
@Table(name = "core_marksRef")
public class Mark extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "mark_id")
    private Integer markId;
    @Column(name = "mark_name")
    private String markName;
    @Column(name = "mark_group")
    private String markGroup;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getMarkId(), this.getMarkName()), this.getMarkId());
    }

}
