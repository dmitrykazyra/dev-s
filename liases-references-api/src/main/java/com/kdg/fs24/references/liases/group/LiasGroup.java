/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.group;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class LiasGroup extends AbstractRefRecord implements ReferenceRec {

    private Integer lias_group_id;
    private String lias_group_name;

    public LiasGroup() {
        super();
    }

    public LiasGroup(final Integer lias_group_id, final String lias_group_name) {
        super();
        this.lias_group_id = lias_group_id;
        this.lias_group_name = lias_group_name;
    }

    //==========================================================================
    public Integer getLias_group_id() {
        return lias_group_id;
    }

    public LiasGroup setLias_group_id(final Integer lias_group_id) {
        this.lias_group_id = lias_group_id;
        return this;
    }

    public String getLias_group_name() {
        return lias_group_name;
    }

    public LiasGroup setLias_group_name(final String lias_group_name) {
        this.lias_group_name = lias_group_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getLias_group_id(), this.getLias_group_name()), this.getLias_group_id());
    }
}
