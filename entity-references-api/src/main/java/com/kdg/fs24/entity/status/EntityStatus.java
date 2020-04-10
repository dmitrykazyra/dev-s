/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.status;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Entity
@Table(name = "core_EntityStatusesRef")
@Data
public class EntityStatus extends AbstractRefRecord implements ReferenceRec {

    @Id
    private Integer entity_status_id;
    private Integer entity_type_id;
    private String entity_status_name;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getEntity_status_id(), this.getEntity_status_name()), this.getEntity_status_id());
    }
}
