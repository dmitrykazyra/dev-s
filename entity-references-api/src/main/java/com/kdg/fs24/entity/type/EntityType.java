/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.type;


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
@Table(name = "core_EntityTypesRef")
@Data
public class EntityType  extends AbstractRefRecord implements ReferenceRec {
    @Id
    private Integer entity_type_id;
    private String entity_type_name;
    private String entity_app_name;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getEntity_type_id(), this.getEntity_type_name()), this.getEntity_type_id());
    }
}
