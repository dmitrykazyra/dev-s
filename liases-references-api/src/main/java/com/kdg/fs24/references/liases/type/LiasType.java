/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.type;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class LiasType extends AbstractRefRecord implements ReferenceRec {

    private Integer lias_type_id;
    private String lias_type_name;

    public LiasType() {
        super();
    }

    public Integer getLias_type_id() {
        return lias_type_id;
    }

    public LiasType setLias_type_id(final Integer lias_type_id) {
        this.lias_type_id = lias_type_id;
        return this;
    }

    public String getLias_type_name() {
        return lias_type_name;
    }

    public LiasType setLias_type_name(final String lias_type_name) {
        this.lias_type_name = lias_type_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getLias_type_id(), this.getLias_type_name()), this.getLias_type_id());
    }
}
