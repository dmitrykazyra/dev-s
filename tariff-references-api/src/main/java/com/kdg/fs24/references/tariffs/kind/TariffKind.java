/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

import com.kdg.fs24.references.api.AbstractRefRecord;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.tariffs.serv.TariffServ;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "TariffKindsRef")
public class TariffKind extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "tariff_kind_id")
    private Integer tariffKindId;

    @ManyToOne
    @JoinColumn(name = "tariff_serv_id", referencedColumnName = "tariff_serv_id")
    private TariffServ tariffServ;

    @Column(name = "tariff_kind_name")
    private String tariffKindName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        //map.put(String.format("%d - %s", this.getTariff_group_id(), this.getTariff_group_name()), this.getTariff_group_id());
    }
}
