/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.marks;


import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;

/**
 *
 * @author kazyra_d
 */
public class MarkValue implements ReferenceRec {

    private Integer entity_mark_id;
    private Integer entity_mark_value_id;
    private String entity_mark_value_name;

    public Integer getEntity_mark_id() {
        return entity_mark_id;
    }

    public void setEntity_mark_id(final Integer entity_mark_id) {
        this.entity_mark_id = entity_mark_id;
    }

    public Integer getEntity_mark_value_id() {
        return entity_mark_value_id;
    }

    public void setEntity_mark_value_id(final Integer entity_mark_value_id) {
        this.entity_mark_value_id = entity_mark_value_id;
    }

    public String getEntity_mark_value_name() {
        return entity_mark_value_name;
    }

    public void setEntity_mark_value_name(final String entity_mark_value_name) {
        this.entity_mark_value_name = entity_mark_value_name;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getEntity_mark_id(), this.getEntity_mark_value_name()), this.getEntity_mark_id());
    }
}
