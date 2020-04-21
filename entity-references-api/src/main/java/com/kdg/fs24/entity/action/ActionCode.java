/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.action;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Entity
@Data
@Table(name = "core_ActionCodesRef")
public class ActionCode extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "action_code")
    private Integer actionCode;
    @Column(name = "action_name")
    private String actionName;
    @Column(name = "app_name")
    private String appName;
    @Column(name = "is_closed")
    private Boolean isClosed;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(this.toString(), this.getActionCode());
    }
}
