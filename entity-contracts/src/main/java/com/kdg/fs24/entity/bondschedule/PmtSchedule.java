/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.bondschedule;

/**
 *
 * @author N76VB
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
//import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.bond.schedule.api.BondScheduleConst;
//import com.kdg.fs24.bond.schedule.collection.BondSchedulesActionClassesService;
//import com.kdg.fs24.jdbc.api.exception.QueryExecutionException;
import com.kdg.fs24.references.bond.schedule.api.PmtScheduleAlg;
import com.kdg.fs24.references.bond.schedule.api.PmtScheduleTerm;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import java.time.LocalDate;
import java.util.Collection;
import com.kdg.fs24.entity.core.api.DefaultEntityStatus;
import com.kdg.fs24.entity.core.api.EntityKindId;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import com.kdg.fs24.entity.core.api.EntityKindsRef;
import com.kdg.fs24.entity.kind.EntityKind;
import com.kdg.fs24.entity.contracts.AbstractEntityContract;
import com.kdg.fs24.entity.core.api.ActionClassesPackages;
import com.kdg.fs24.entity.core.api.EntityStatusesRef;
import com.kdg.fs24.entity.status.EntityStatusId;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@EntityTypeId(entity_type_id = BondScheduleConst.BONDSCHEDULE, entity_type_name = "График погашения обязательства")
@EntityKindsRef(
        kind_id = {
            @EntityKindId(entity_kind_id = BondScheduleConst.EK_BONDSCHEDULE_MAIN_DEBT,
                    entity_type_id = BondScheduleConst.BONDSCHEDULE,
                    entity_kind_name = "График погашения основного долга")
            ,
            @EntityKindId(entity_kind_id = BondScheduleConst.EK_BONDSCHEDULE_PERC,
                    entity_type_id = BondScheduleConst.BONDSCHEDULE,
                    entity_kind_name = "График погашения %")
            ,
                        @EntityKindId(entity_kind_id = BondScheduleConst.EK_BONDSCHEDULE_COMM,
                    entity_type_id = BondScheduleConst.BONDSCHEDULE,
                    entity_kind_name = "График погашения комиссии")
        })
@EntityStatusesRef(
        entiy_status = {
            @EntityStatusId(
                    entity_type_id = BondScheduleConst.BONDSCHEDULE,
                    entity_status_id = BondScheduleConst.ES_DEFAULT_STATUS,
                    entity_status_name = "Действующий график")
        })
@DefaultEntityStatus(entity_status = BondScheduleConst.ES_DEFAULT_STATUS)
@Data
@Entity
@Table(name = "core_PmtSchedules")
@PrimaryKeyJoinColumn(name = "schedule_id", referencedColumnName = "entity_id")
@ActionClassesPackages(pkgList = {"com.kdg.fs24.entity.bondschedule.actions"})
public class PmtSchedule extends AbstractActionEntity {

    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id", updatable = false)
    private AbstractEntityContract entityContract;

    @ManyToOne
    @JoinColumn(name = "entity_kind_id", referencedColumnName = "entity_kind_id", updatable = false)
    private EntityKind entityKind;

    @ManyToOne
    @JoinColumn(name = "pmt_term_id", referencedColumnName = "pmt_term_id", updatable = false)
    private PmtScheduleTerm pmtScheduleTerm;

    @ManyToOne
    @JoinColumn(name = "schedule_alg_id", referencedColumnName = "schedule_alg_id", updatable = false)
    private PmtScheduleAlg pmtScheduleAlg;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "last_date")
    private LocalDate lastDate;

    @OneToMany(mappedBy = "pmtSchedule", cascade = CascadeType.ALL)
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
    private Collection<PmtScheduleLine> pmtScheduleLines;

    //==========================================================================
}
