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

/**
 *
 * @author N76VB
 */
@Entity
@Data
@Table(name = "core_entityMarks")
//@PrimaryKeyJoinColumns(value = {
//    @PrimaryKeyJoinColumn(name = "entity_id", referencedColumnName = "entity_id")
//    , @PrimaryKeyJoinColumn(name = "action_id", referencedColumnName = "action_id")})
@IdClass(EntityMarkPK.class)
public class EntityMark {

    @Id
    @PrimaryKeyJoinColumn(name = "entity_id", referencedColumnName = "entity_id")
    private ActionEntity entity;
    @Id
    @PrimaryKeyJoinColumn(name = "action_id", referencedColumnName = "action_id")
    private Action action;
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "mark_id", referencedColumnName = "mark_id")
//    private Mark mark;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumns(value = {
        @JoinColumn(name = "mark_id", referencedColumnName = "mark_id"),
        @JoinColumn(name = "mark_value_id", referencedColumnName = "mark_value_id")})
    private MarkValue markValue;
}
