/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.debts;

import java.io.Serializable;
import lombok.Data;
import java.time.LocalDate;

/**
 *
 * @author N76VB
 */
@Data
public class LiasRestPK implements Serializable {

    private Lias lias;
    private Integer restType;
    private LocalDate restDate;
}
