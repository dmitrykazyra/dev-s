/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.actiontype;

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
@Table(name = "liasDebtStatesRef")
public class LiasActionType extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "action_type_id")
    private Integer actionTypeId;
    @Column(name = "change_rest_tag")
    private Boolean changeRestTtag;
    @Column(name = "action_type_name")
    private String actionTypeName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getActionTypeId(), this.getActionTypeName()), this.getActionTypeId());
    }
}
