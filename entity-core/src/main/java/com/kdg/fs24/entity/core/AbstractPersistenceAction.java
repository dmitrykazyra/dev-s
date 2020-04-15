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
import lombok.Data;

/**
 *
 * @author N76VB
 */
@PersistenceSetup(
        persistence_unit = "core_persistence",
        table = "core_Actions")
@Entity
@Data
@Table(name = "core_Actions")
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractPersistenceAction<T extends ActionEntity>
        implements Action<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_action_id")
    @SequenceGenerator(name = "seq_action_id", sequenceName = "seq_action_id", allocationSize = 1)
    @Column(name = "action_id", updatable = false)
    private Long actionId;
    @JoinColumn(name = "entity_id", referencedColumnName = "entity_id")
    private T entity;
    @Column(name = "user_id", updatable = false)
    private Integer userId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "action_code", referencedColumnName = "action_code", updatable = false)
    private ActionCode actionCode;
    @Column(name = "execute_date", updatable = false)
    private LocalDateTime executeDate;
    @Column(name = "action_address", updatable = false)
    private String actionAddress;
    @Column(name = "err_msg")
    private String errMsg;
    @Column(name = "action_duration", updatable = false)
    private LocalTime actionDuration;
    @Column(name = "notes", updatable = false)
    private String notes;

}
