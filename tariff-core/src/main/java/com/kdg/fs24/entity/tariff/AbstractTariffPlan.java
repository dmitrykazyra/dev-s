/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.tariff;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.entity.tariff.api.TariffPlan;
import com.kdg.fs24.entity.core.api.ActionClassesCollectionLink;
import com.kdg.fs24.entity.core.api.DefaultEntityStatus;
import com.kdg.fs24.entity.core.api.EntityStatusesRef;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import com.kdg.fs24.entity.kind.EntityKind;
import com.kdg.fs24.entity.status.EntityStatusId;
import com.kdg.fs24.references.tariffs.api.TariffConst;
import com.kdg.fs24.references.tariffs.kind.TariffKind;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.entity.tariff.api.TariffKindProcessor;
//import com.kdg.fs24.tariffs.references.TariffReferencesService;
import com.kdg.fs24.entity.core.api.ActionClassesPackages;
import java.util.Collection;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "tariffPlans")
@PrimaryKeyJoinColumn(name = "tariff_plan_id", referencedColumnName = "entity_id")
@EntityTypeId(entity_type_id = TariffConst.ENTITY_TARIFF_PLAN,
        entity_type_name = "Тарифный план")
@EntityStatusesRef(
        entiy_status = {
            @EntityStatusId(
                    entity_type_id = TariffConst.ENTITY_TARIFF_PLAN,
                    entity_status_id = SysConst.ES_VALID,
                    entity_status_name = "Действующий тарифный план")
            ,
            @EntityStatusId(
                    entity_type_id = TariffConst.ENTITY_TARIFF_PLAN,
                    entity_status_id = SysConst.ES_CLOSED,
                    entity_status_name = "Закрытый тарифный план")
            ,
            @EntityStatusId(
                    entity_type_id = TariffConst.ENTITY_TARIFF_PLAN,
                    entity_status_id = SysConst.ES_CANCELLED,
                    entity_status_name = "Аннулированный тарифный план")
        })
@DefaultEntityStatus(entity_status = SysConst.ES_VALID)
@ActionClassesPackages(pkgList = {"com.kdg.fs24.entity.tariff.actions"})
public class AbstractTariffPlan extends AbstractActionEntity
        implements TariffPlan {

    @Column(name = "tariff_plan_name")
    private String tariffPlanName;
    @Column(name = "tariff_plan_code")
    private String tariffPlanCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SysConst.DATE_FORMAT)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "actual_date")
    private LocalDate actualDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SysConst.DATE_FORMAT)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "finish_date")
    private LocalDate finishDate;

    // вид тарифного плана
    @ManyToOne
    @JoinColumn(name = "tariff_plan_kind_id", referencedColumnName = "entity_kind_id")
    private EntityKind planKind;

    // коллекция тарифицируемых услуг в тарифном плане
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tariffPlan")
    private Collection<TariffPlan2Serv> tariffServs = ServiceFuncs.<TariffPlan2Serv>createCollection();

    //==========================================================================
    public Long getTariffPlanId() {
        return super.getEntity_id();
    }

    //==========================================================================
    public void addServKindId(final TariffKind tariffKind,
            final LocalDate aDate,
            final LocalDate fDate,
            final ServProcessor servProcessor) {

        final TariffPlan2Serv tariffPlan2Serv = NullSafe.createObject(TariffPlan2Serv.class);

        tariffPlan2Serv.setActualDate(aDate);
        tariffPlan2Serv.setCloseDate(fDate);
        tariffPlan2Serv.setTariffKind(tariffKind);
        tariffPlan2Serv.setTariffServ(tariffKind.getTariffServ());
        tariffPlan2Serv.setTariffPlan(this);
        
        servProcessor.processServ(tariffPlan2Serv);
        
        tariffServs.add(tariffPlan2Serv);

    }

}
