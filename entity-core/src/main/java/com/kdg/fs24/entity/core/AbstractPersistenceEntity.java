/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.core;

import com.kdg.fs24.persistence.api.PersistenceSetup;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.entity.type.EntityType;
import com.kdg.fs24.entity.status.EntityStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 *
 * @author N76VB
 */
@PersistenceSetup(
        persistence_unit = "core_persistence",
        table = "core_Entities")
@Entity
@Table(name = "core_Entities")
public class AbstractPersistenceEntity<T extends AbstractAction> {

    @Transient
    private T entity;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_action_id")
    private Long entity_id;
    @JoinColumn
    private EntityType entityType;
    @JoinColumn
    private EntityStatus entityStatus;
    @Column(name = "creation_date")
    private LocalDateTime creation_date;
    @Column(name = "close_date")
    private LocalDate close_date;
    @Column(name = "last_modify")
    private LocalDateTime last_modify;

    //==========================================================================
    public Long getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(Long entity_id) {
        this.entity_id = entity_id;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(EntityStatus entityStatus) {
        this.entityStatus = entityStatus;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public LocalDate getClose_date() {
        return close_date;
    }

    public void setClose_date(LocalDate close_date) {
        this.close_date = close_date;
    }

    public LocalDateTime getLast_modify() {
        return last_modify;
    }

    public void setLast_modify(LocalDateTime last_modify) {
        this.last_modify = last_modify;
    }

}
