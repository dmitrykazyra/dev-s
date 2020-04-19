/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.baseassettype;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class LiasBaseAssetType extends AbstractRefRecord implements ReferenceRec {

    private Integer base_asset_type_id;
    private String base_asset_type_name;

    public LiasBaseAssetType() {
        super();
    }

    public LiasBaseAssetType(final Integer base_asset_type_id, final String base_asset_type_name) {
        super();
        this.base_asset_type_id = base_asset_type_id;
        this.base_asset_type_name = base_asset_type_name;
    }

    //==========================================================================
    public Integer getBase_asset_type_id() {
        return base_asset_type_id;
    }

    public LiasBaseAssetType setBase_asset_type_id(final Integer base_asset_type_id) {
        this.base_asset_type_id = base_asset_type_id;
        return this;
    }

    public String getBase_asset_type_name() {
        return base_asset_type_name;
    }

    public LiasBaseAssetType setBase_asset_type_name(final String base_asset_type_name) {
        this.base_asset_type_name = base_asset_type_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getBase_asset_type_id(), this.getBase_asset_type_name()), this.getBase_asset_type_id());
    }

}
