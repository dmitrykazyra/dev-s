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
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "liasRests")
@IdClass(LiasRestPK.class)
public class LiasRest extends ObjectRoot {

    @Id
    private Integer lias_id;
    @Id
    private Integer rest_type;
    @Id
    private LocalDate rest_date;
    private BigDecimal rest;
}
