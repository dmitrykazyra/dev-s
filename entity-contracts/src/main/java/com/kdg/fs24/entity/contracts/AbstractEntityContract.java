/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.contracts;

import com.kdg.fs24.entity.core.AbstractActionEntity;
import javax.persistence.*;
import com.kdg.fs24.entity.contract.subjects.ContractSubject;
import com.kdg.fs24.counterparties.api.Counterparty;
import lombok.Data;
import com.kdg.fs24.entity.kind.EntityKind;
import com.kdg.fs24.references.application.currency.Currency;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.kdg.fs24.tariff.core.api.TariffPlan;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "core_EntityContracts")
@PrimaryKeyJoinColumn(name = "contract_id", referencedColumnName = "entity_id")
public class AbstractEntityContract extends AbstractActionEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JoinColumn(name = "entity_type_id", updatable = false, insertable = true)
    @JoinColumns({
        @JoinColumn(name = "contract_subject_id", referencedColumnName = "contract_subject_id")})
    private ContractSubject contractSubject;
    @Column(name = "contract_num")
    private String contractNum;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Counterparty counterparty;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EntityKind entityKind;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Currency currency;
    @Column(name = "contract_date")
    private LocalDate contractDate;
    @Column(name = "begin_date")
    private LocalDate beginDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "contract_summ")
    private BigDecimal contractSumm;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TariffPlan tariffPlan;
}
