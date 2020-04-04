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
import lombok.Data;

/**
 *
 * @author N76VB
 */
@PersistenceSetup(
        persistence_unit = "core_persistence",
        table = "core_Entities")
@Entity
@Table(name = "core_Entities")
@Data
public class AbstractPersistenceEntity {

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

}
