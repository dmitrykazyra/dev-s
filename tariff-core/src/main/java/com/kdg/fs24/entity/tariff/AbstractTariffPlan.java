/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.tariff;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.kdg.fs24.entity.tariff.api.TariffPlan;
import com.kdg.fs24.entity.core.api.ActionClassesCollectionLink;
import com.kdg.fs24.entity.core.api.DefaultEntityStatus;
import com.kdg.fs24.entity.core.api.EntityKindId;
import com.kdg.fs24.entity.core.api.EntityStatusesRef;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import com.kdg.fs24.entity.kind.EntityKind;
import com.kdg.fs24.entity.reference.EntityReferencesService;
import com.kdg.fs24.entity.status.EntityStatusId;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.references.tariffs.api.TariffConst;
import com.kdg.fs24.references.tariffs.kind.TariffKind;
import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.entity.tariff.api.TariffKindProcessor;
//import com.kdg.fs24.tariffs.references.TariffReferencesService;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
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
    private EntityKind entityKind;
//
//    // коллекция тарифов в плане
    @Transient
    @ManyToMany
    private Collection<TariffKind> tariffKinds;

    public Long getTariffPlanId() {
        return super.getEntity_id();
    }

    //==========================================================================
    @Override
    public TariffPlan addTariffKind(final TariffKind tariffKind) {
        this.getTariffKinds()
                .add(tariffKind);

        return this;
    }

    //==========================================================================
    @Override
    public TariffKind getTariffKind(final Integer serv_id) {

        return (TariffKind) ServiceFuncs.<TariffKind>getCollectionElement(this.getTariffKinds(),
                tk -> tk.getTariff_serv_id().equals(serv_id),
                String.format("ServId is not found (%d)", serv_id)
        );
    }

    //==========================================================================
    @Override
    public TariffPlan createTariffKind(final int tariffKindId, final TariffKindProcessor tkp) {

//        NullSafe.create()
//                .execute(() -> {
//
//                    final Integer existsServId = ServiceLocator
//                            .find(TariffReferencesService.class)
//                            .findTariffServByKindId(tariffKindId);
//
//                    // проверка на уникальность - два тарифа с одинаковым serv_id не могут быть в одном тарифном плане
//                    final T existTariffKind = ServiceFuncs.<T>getCollectionElement_silent(this.getTariffKinds(),
//                            p -> p.getTariff_serv_id().equals(existsServId));
//
//                    if (null != existTariffKind) {
//                        throw new DuplicateServIdException(String.format("ServId is not unique (serv_id=%d)", existsServId));
//                    }
//
//                    final T tariffTemplate = ServiceLocator.find(TariffReferencesService.class)
//                            .getTariffKindById(tariffKindId);
//
//                    final T tariffTemplateImpl = (T) tariffTemplate.createTariffKindCopy();
//
//                    tkp.processTariffKind(tariffTemplateImpl);
//
//                    this.getTariffKinds()
//                            .add((T) tariffTemplateImpl);
//                })
//                .throwException();
        return this;
    }

    @Override
    public TariffPlan addTariffKind(final int tariffKind) {
//        this.getTariffKinds()
//                .add(ServiceLocator.find(TariffReferencesService.class)
//                        .getTariffKindById(tariffKind));

        return this;
    }

    //==========================================================================
//    @Override
//    public void saveEntityInstance() {
//        //super.saveEntityInstance();
//
//        // сохранение плана
//        this.getDbService()
//                .createCallQuery("{call tariff_insertorupdate_tariffplan(:ID, :KI, :TPK, :TPN, :AD, :FD)}")
//                .setParamByName("ID", this.getEntity_id())
//                .setParamByName("KI", this.getEntityKind().getEntity_kind_id())
//                .setParamByName("TPK", this.getTariff_plan_code())
//                .setParamByName("TPN", this.getTariff_plan_name())
//                .setParamByName("AD", this.getActual_date())
//                .setParamByName("FD", this.getFinish_date())
//                .execCallStmt();
//        // сохранение тарифицируемых услуг на плане
//        this.getDbService()
//                .createCallBath("{call tariff_insertorupdate_tariffplan2servid(:PID, :SERV, :KIND, :AD, :CD)}")
//                .execBatch(stmt -> {
//
//                    this.getTariffKinds()
//                            .stream()
//                            .forEach((tariffKind) -> {
//                                stmt.setParamByName("PID", this.getEntity_id());
//                                stmt.setParamByName("SERV", tariffKind.getTariff_serv_id());
//                                stmt.setParamByName("KIND", tariffKind.getTariff_kind_id());
//                                stmt.setParamByName("AD", tariffKind.getActual_date());
//                                stmt.setParamByName("CD", tariffKind.getClose_date());
//                                stmt.addBatch();
//
//                                //tariffKind.getTariffRates().
//                            });
//                });
//
//        // сохранение тарифных ставок
//        this.getTariffKinds()
//                .stream()
//                .forEach((tariffKind) -> {
//                    tariffKind.store(this.getEntity_id());
//
//                    tariffKind
//                            .getTariffRate()
//                            .store();
//                });
//    }
}
