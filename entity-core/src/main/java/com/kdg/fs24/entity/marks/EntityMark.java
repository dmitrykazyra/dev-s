/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.marks;

import javax.persistence.*;
import lombok.Data;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.entity.core.api.Action;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import com.kdg.fs24.entity.core.AbstractPersistenceEntity;
import com.kdg.fs24.entity.core.AbstractPersistenceAction;

/**
 *
 * @author N76VB
 */
@Entity
@Data
@Table(name = "core_entityMarks")
@PrimaryKeyJoinColumns(value = {
    @PrimaryKeyJoinColumn(name = "entity_id", referencedColumnName = "entity_id")
    , @PrimaryKeyJoinColumn(name = "action_id", referencedColumnName = "action_id")})
@IdClass(EntityMarkPK.class)
public class EntityMark implements PersistenceEntity {

    @Id
//    @PrimaryKeyJoinColumn(name = "entity_id", referencedColumnName = "entity_id")
    @ManyToOne(targetEntity = AbstractPersistenceEntity.class)
    @JoinColumn(name = "entity_id", referencedColumnName = "entity_id")
    private ActionEntity entity;
    @Id
//    @PrimaryKeyJoinColumn(name = "action_id", referencedColumnName = "action_id")
    @ManyToOne(targetEntity = AbstractPersistenceAction.class)
    @JoinColumn(name = "action_id", referencedColumnName = "action_id")
    private Action action;
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "mark_id", referencedColumnName = "mark_id")
//    private Mark mark;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumns(value = {
        @JoinColumn(name = "mark_id", referencedColumnName = "mark_id")
        ,
        @JoinColumn(name = "mark_value_id", referencedColumnName = "mark_value_id")})
    private MarkValue markValue;
    @Column(name = "mark_direction")
    private Boolean direction;
}
