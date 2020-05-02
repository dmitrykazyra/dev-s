/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.liases.api;

import java.io.Serializable;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 *
 * @author N76VB
 */
@Data
public class LiasRestPK implements Serializable {

    private Integer lias_id;
    private Integer rest_type;
    private LocalDate rest_date;
}
