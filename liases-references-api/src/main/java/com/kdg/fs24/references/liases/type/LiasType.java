/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.type;

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
@Table(name = "liasTypesRef")
public class LiasType extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "lias_type_id")
    private Integer liasTypeId;
    @Column(name = "lias_type_name")
    private String liasTypeName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getLiasTypeId(), this.getLiasTypeName()), this.getLiasTypeId());
    }
}
