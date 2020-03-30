/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.status;


import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class EntityStatus  extends AbstractRefRecord implements ReferenceRec {

    private Integer entity_status_id;
    private Integer entity_type_id;
    private String entity_status_name;

    public EntityStatus() {
        super();
    }

    public Integer getEntity_type_id() {
        return entity_type_id;
    }

    public Integer getEntity_status_id() {
        return entity_status_id;
    }

    public String getEntity_status_name() {
        return entity_status_name;
    }

    public void setEntity_status_id(final Integer entity_status_id) {
        this.entity_status_id = entity_status_id;
    }

    public void setEntity_type_id(final Integer entity_type_id) {
        this.entity_type_id = entity_type_id;
    }

    public void setEntity_status_name(final String entity_status_name) {
        this.entity_status_name = entity_status_name;
    }
    
        @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getEntity_status_id(), this.getEntity_status_name()), this.getEntity_status_id());
    }
}
