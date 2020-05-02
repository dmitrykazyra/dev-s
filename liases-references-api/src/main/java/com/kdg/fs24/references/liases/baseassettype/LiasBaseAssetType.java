/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.baseassettype;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "liasBaseAssetTypesRef")
public class LiasBaseAssetType extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "base_asset_type_id")
    private Integer baseAssetTypeId;
    @Column(name = "base_asset_type_name")
    private String baseAssetTypeName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getBaseAssetTypeId(), this.getBaseAssetTypeName()), this.getBaseAssetTypeId());
    }

}
