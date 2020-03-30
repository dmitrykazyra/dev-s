/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.action;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import javax.persistence.*;


/**
 *
 * @author kazyra_d
 */
@Entity
@Table(name = "core_ActionsCodesRef")
public class ActionCode extends AbstractRefRecord implements ReferenceRec {
    @Id
    private Integer action_code;
    private String action_name;
    private String app_name;
    private Boolean is_closed;

    public ActionCode() {
        super();
    }

    //==========================================================================
    public Integer getAction_code() {
        return action_code;
    }

    public void setAction_code(final Integer action_code) {
        this.action_code = action_code;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(final String action_name) {
        this.action_name = action_name;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(final String app_name) {
        this.app_name = app_name;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getAction_code(), this.getAction_name()), this.getAction_code());
    }

    public Boolean getIs_closed() {
        return is_closed;
    }

    public void setIs_closed(final Boolean is_closed) {
        this.is_closed = is_closed;
    }
}
