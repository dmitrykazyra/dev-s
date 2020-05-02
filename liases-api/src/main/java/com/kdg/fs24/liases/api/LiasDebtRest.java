/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.liases.api;

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
    private Integer debt_id;
    @Id
    private Integer rest_type;
    @Id
    private LocalDate rest_date;
    private BigDecimal rest;

}
