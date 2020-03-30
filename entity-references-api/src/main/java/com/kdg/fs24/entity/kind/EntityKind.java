/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.kind;


import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class EntityKind  extends AbstractRefRecord implements ReferenceRec {

    private Integer entity_kind_id;
    private Integer entity_type_id;
    private String entity_kind_name;

    public EntityKind() {
        super();
    }

    public Integer getEntity_kind_id() {
        return entity_kind_id;
    }

    public void setEntity_kind_id(final Integer entity_kind_id) {
        this.entity_kind_id = entity_kind_id;
    }

    public Integer getEntity_type_id() {
        return entity_type_id;
    }

    public void setEntity_type_id(final Integer entity_type_id) {
        this.entity_type_id = entity_type_id;
    }

    public String getEntity_kind_name() {
        return entity_kind_name;
    }

    public void setEntity_kind_name(final String entity_kind_name) {
        this.entity_kind_name = entity_kind_name;
    }
    //==========================================================================

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getEntity_kind_id(), this.getEntity_kind_name()), this.getEntity_kind_id());
    }

}
