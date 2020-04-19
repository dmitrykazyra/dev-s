/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.kind;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class LiasKind extends AbstractRefRecord implements ReferenceRec {

    private Integer lias_kind_id;
    private Boolean Is_claim;
    private Integer lias_group_id;
    private String lias_kind_name;

    public LiasKind() {
        super();
    }

    public LiasKind(final Integer lias_kind_id, final Integer lias_group_id, final Boolean Is_claim, final String lias_kind_name) {
        super();
        this.lias_kind_id = lias_kind_id;
        this.Is_claim = Is_claim;
        this.lias_group_id = lias_group_id;
        this.lias_kind_name = lias_kind_name;
    }

    //==========================================================================
    public Integer getLias_kind_id() {
        return lias_kind_id;
    }

    public LiasKind setLias_kind_id(final Integer lias_kind_id) {
        this.lias_kind_id = lias_kind_id;
        return this;
    }

    public Boolean getIs_claim() {
        return Is_claim;
    }

    public LiasKind setIs_claim(final Boolean Is_claim) {
        this.Is_claim = Is_claim;
        return this;
    }

    public Integer getLias_group_id() {
        return lias_group_id;
    }

    public LiasKind setLias_group_id(final Integer lias_group_id) {
        this.lias_group_id = lias_group_id;
        return this;
    }

    public String getLias_kind_name() {
        return lias_kind_name;
    }

    public LiasKind setLias_kind_name(final String lias_kind_name) {
        this.lias_kind_name = lias_kind_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getLias_kind_id(), this.getLias_kind_name()), this.getLias_kind_id());
    }
}
