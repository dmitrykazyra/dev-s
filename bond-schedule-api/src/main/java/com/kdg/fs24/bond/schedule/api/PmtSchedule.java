/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.bond.schedule.api;

import java.util.List;
import com.kdg.fs24.bond.schedule.references.api.PmtScheduleTerm;
import java.time.LocalDate;
import com.kdg.fs24.bond.schedule.references.api.PmtScheduleAlg;

/**
 *
 * @author kazyra_d
 */
public interface PmtSchedule {

    Long getSchedule_id();

    Long getContract_id();

    void setContract_id(Long contract_id);

//    EntityContract getEntityContract();
    Integer getEntityKindId();

    PmtScheduleTerm getPmtScheduleTerm();

    PmtScheduleAlg getPmtScheduleAlg();

    LocalDate getFrom_date();

    LocalDate getLast_date();

    List<PmtScheduleLine> getPmtScheduleLines();
            
    void saveEntityInstance();
}
