/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.bondschedule;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
public class PmtScheduleLinePK implements Serializable {

    private PmtSchedule pmtSchedule;
    private LocalDate actualDate;
    private LocalDate fromDate;
    private LocalDate toDate;

}