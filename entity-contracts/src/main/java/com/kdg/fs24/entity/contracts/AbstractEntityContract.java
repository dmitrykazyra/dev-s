/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.contracts;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import javax.persistence.*;
import com.kdg.fs24.entity.contract.subjects.ContractSubject;
import com.kdg.fs24.entity.counterparties.api.Counterparty;
import com.kdg.fs24.entity.core.AbstractPersistenceAction;
import com.kdg.fs24.entity.core.api.Action;
import lombok.Data;
import com.kdg.fs24.entity.kind.EntityKind;
import com.kdg.fs24.references.application.currency.Currency;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.kdg.fs24.entity.tariff.api.TariffPlan;
import com.kdg.fs24.entity.tariff.AbstractTariffPlan;
import java.util.Collection;
import com.kdg.fs24.entity.marks.EntityMark;
import com.kdg.fs24.entity.debts.LiasDebt;
import com.kdg.fs24.entity.bondschedule.PmtSchedule;
import com.kdg.fs24.entity.tariff.TariffCalcRecord;
import com.kdg.fs24.entity.tariff.TariffPlan2Serv;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "core_EntityContracts")
@PrimaryKeyJoinColumn(name = "contract_id", referencedColumnName = "entity_id")
public abstract class AbstractEntityContract extends AbstractActionEntity {

    @ManyToOne
    //@JoinColumn(name = "entity_type_id", updatable = false, insertable = true)

    @JoinColumn(name = "contract_subject_id", referencedColumnName = "contract_subject_id", updatable = false)
    private ContractSubject contractSubject;
    // 
    //--------------------------------------------------------------------------    

    @Column(name = "contract_num")
    private String contractNum;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "counterparty_id", referencedColumnName = "counterparty_id")
    private Counterparty counterparty;
    // 
    //--------------------------------------------------------------------------    

    @ManyToOne
    @JoinColumn(name = "entity_kind_id", referencedColumnName = "entity_kind_id", updatable = false)
    private EntityKind entityKind;
    // 
    //--------------------------------------------------------------------------    

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
    private Currency currency;
    // 
    //--------------------------------------------------------------------------    

    @Column(name = "contract_date")
    private LocalDate contractDate;
    // 
    //--------------------------------------------------------------------------    

    @Column(name = "begin_date")
    private LocalDate beginDate;
    // 
    //--------------------------------------------------------------------------    

    @Column(name = "end_date")
    private LocalDate endDate;
    // 
    //--------------------------------------------------------------------------    

    @Column(name = "contract_summ")
    private BigDecimal contractSumm;
    // 
    //--------------------------------------------------------------------------    
    @ManyToOne(targetEntity = AbstractTariffPlan.class)
    @JoinColumn(name = "tariff_plan_id", referencedColumnName = "tariff_plan_id")
    private AbstractTariffPlan tariffPlan;
    // отметки на сущности
    //--------------------------------------------------------------------------
    @OneToMany
    @JoinColumn(name = "entity_id", referencedColumnName = "contract_id")
    private Collection<EntityMark> entityMarks;
    // исполенные действия
    //--------------------------------------------------------------------------
    @OneToMany(targetEntity = AbstractPersistenceAction.class, mappedBy = "entity")
//    @JoinColumn(name = "entity_id", referencedColumnName = "entity_id")
    private Collection<Action> entityActions;
    //==========================================================================
    // задолженности по договору
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "debtContract")
    //@JoinColumn(name = "contract_id", referencedColumnName = "contract_id")
//    @OneToMany(mappedBy = "debtContract", cascade = CascadeType.ALL)
    private Collection<LiasDebt> contractDebts;
    //==========================================================================
    //графики по договору
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entityContract")
    //@JoinColumn(name = "contract_id", referencedColumnName = "contract_id")
    private Collection<PmtSchedule> pmtSchedules;
    //==========================================================================
    // рассчеты сумм калькуляций
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entity")
    //@JoinColumn(name = "entity_id", referencedColumnName = "entity_id")
    private Collection<TariffCalcRecord> tariffCalcRecords = ServiceFuncs.<TariffCalcRecord>createCollection();

}
