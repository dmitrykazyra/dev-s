/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.type;


import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class EntityType  extends AbstractRefRecord implements ReferenceRec {

    private Integer entity_type_id;
    private String entity_type_name;
    private String entity_app_name;

    public EntityType() {
        super();
    }

    public Integer getEntity_type_id() {
        return entity_type_id;
    }

    public String getEntity_type_name() {
        return entity_type_name;
    }

    public void setEntity_type_id(final Integer entity_type_id) {
        this.entity_type_id = entity_type_id;
    }

    public void setEntity_type_name(final String entity_type_name) {
        this.entity_type_name = entity_type_name;
    }

    public String getEntity_app_name() {
        return entity_app_name;
    }

    public void setEntity_app_name(final String entity_app_name) {
        this.entity_app_name = entity_app_name;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getEntity_type_id(), this.getEntity_type_name()), this.getEntity_type_id());
    }
}
