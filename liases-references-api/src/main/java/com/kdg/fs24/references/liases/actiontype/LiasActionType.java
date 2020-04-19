/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.actiontype;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class LiasActionType extends AbstractRefRecord implements ReferenceRec {

    private Integer action_type_id;
    private Boolean change_rest_tag;
    private String action_type_name;

    public LiasActionType() {
        super();
    }

    public Boolean getChange_rest_tag() {
        return change_rest_tag;
    }

    public LiasActionType setChange_rest_tag(final Boolean change_rest_tag) {
        this.change_rest_tag = change_rest_tag;
        return this;
    }

    public Integer getAction_type_id() {
        return action_type_id;
    }

    public LiasActionType setAction_type_id(final Integer action_type_id) {
        this.action_type_id = action_type_id;
        return this;
    }

    public String getAction_type_name() {
        return action_type_name;
    }

    public LiasActionType setAction_type_name(final String action_type_name) {
        this.action_type_name = action_type_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getAction_type_id(), this.getAction_type_name()), this.getAction_type_id());
    }
}
