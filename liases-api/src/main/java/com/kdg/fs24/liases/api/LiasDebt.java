/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.liases.api;

import com.kdg.fs24.bond.schedule.api.PmtSchedule;
import com.kdg.fs24.references.application.currency.Currency;
import com.kdg.fs24.references.liases.baseassettype.LiasBaseAssetType;
import com.kdg.fs24.references.liases.debtstate.LiasDebtState;
import com.kdg.fs24.references.liases.kind.LiasKind;
import com.kdg.fs24.references.liases.type.LiasType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import com.kdg.fs24.lias.opers.napi.NewLiasOper;
import com.kdg.fs24.lias.opers.attrs.*;
import com.kdg.fs24.liases.exception.LiasDoesNotExists;
import com.kdg.fs24.liases.exception.LiasRestIsNegative;
import com.kdg.fs24.application.core.locale.NLS;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.api.ObjectRoot;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import com.kdg.fs24.application.core.service.funcs.CustomCollectionImpl;
import com.kdg.fs24.application.core.service.funcs.FilterComparator;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.lias.opers.napi.SaveAccretionHist;
import com.kdg.fs24.liases.api.LiasAction;
import com.kdg.fs24.lias.opers.api.LiasOpersConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "liasDebts")
public class LiasDebt extends ObjectRoot implements PersistenceEntity {

    @Transient
    private Integer rowNum;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_debt_id")
    @SequenceGenerator(name = "seq_debt_id", sequenceName = "seq_debt_id", allocationSize = 1)
    @Column(name = "debt_id", updatable = false)
    private Integer debtId;
    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
    private Currency currency;

    @JoinColumn(name = "lias_debt_state_id", referencedColumnName = "lias_debt_state_id")
    private LiasDebtState liasDebtState;

    @JoinColumn(name = "lias_kind_id", referencedColumnName = "lias_kind_id")
    private LiasKind liasKind;

    @JoinColumn(name = "lias_type_id", referencedColumnName = "lias_type_id")
    private LiasType liasType;

    @JoinColumn(name = "base_asset_type_id", referencedColumnName = "base_asset_type_id")
    private LiasBaseAssetType liasBaseAssetType;

    @Column(name = "debt_start_date")
    private LocalDate debtStartDate;
    @Column(name = "debt_final_date")
    private LocalDate debtFinalDate;

    @OneToMany
    @JoinColumn(name = "debt_id", referencedColumnName = "debt_id")
    private Collection<Lias> liases;

    @OneToMany
    @JoinColumn(name = "debt_id", referencedColumnName = "debt_id")    
    private Collection<LiasDebtRest> debtRests;

}
