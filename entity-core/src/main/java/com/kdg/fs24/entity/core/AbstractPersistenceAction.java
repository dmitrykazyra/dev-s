/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.core;

import com.kdg.fs24.entity.core.api.Action;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.entity.action.ActionCode;
import com.kdg.fs24.persistence.api.PersistenceSetup;
import com.kdg.fs24.application.core.sysconst.SysConst;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.*;

/**
 *
 * @author N76VB
 */
@PersistenceSetup(
        persistence_unit = "core_persistence",
        table = "core_Actions")
@Entity
@Table(name = "core_Actions")
public class AbstractPersistenceAction<T extends ActionEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_action_id")
    private Long actionId;
    @Transient
    private T entity;
    @Column
    private Integer user_id;
    @JoinColumn
    private ActionCode actionCode;
    @Column
    private LocalDateTime execute_date;
    @Column
    private String action_address;
    @Column
    private String err_msg;
    @Column
    private LocalTime action_duration;
    @Column
    private String notes;

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public ActionCode getActionCode() {
        return actionCode;
    }

    public void setActionCode(ActionCode actionCode) {
        this.actionCode = actionCode;
    }

    public LocalDateTime getExecute_date() {
        return execute_date;
    }

    public void setExecute_date(LocalDateTime execute_date) {
        this.execute_date = execute_date;
    }

    public String getAction_address() {
        return action_address;
    }

    public void setAction_address(String action_address) {
        this.action_address = action_address;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public LocalTime getAction_duration() {
        return action_duration;
    }

    public void setAction_duration(LocalTime action_duration) {
        this.action_duration = action_duration;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
