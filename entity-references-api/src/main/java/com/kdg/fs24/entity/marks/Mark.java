/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.marks;


import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class Mark  extends AbstractRefRecord implements ReferenceRec {

    private Integer mark_id;
    private String mark_name;
    private String mark_group;

    public Integer getMark_id() {
        return mark_id;
    }

    public void setMark_id(final Integer mark_id) {
        this.mark_id = mark_id;
    }

    public String getMark_name() {
        return mark_name;
    }

    public void setMark_name(final String mark_name) {
        this.mark_name = mark_name;
    }

    public String getMark_group() {
        return mark_group;
    }

    public void setMark_group(final String mark_group) {
        this.mark_group = mark_group;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getMark_id(), this.getMark_name()), this.getMark_id());
    }

}
