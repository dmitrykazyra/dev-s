/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.liases.api;

/**
 *
 * @author N76VB
 */
import com.kdg.fs24.application.core.api.ObjectRoot;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "liasDebtRests")
@IdClass(LiasDebtRestPK.class)
public class LiasDebtRest extends ObjectRoot implements PersistenceEntity {

    @Id
    @Column(name = "debt_id")
    private Integer debtId;
    @Id
    @Column(name = "rest_typed")
    private Integer restType;
    @Id
    @Column(name = "rest_dated")
    private LocalDate restDate;
    private BigDecimal rest;

}
