/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.debts;

import com.kdg.fs24.application.core.service.funcs.CustomCollectionImpl;
import com.kdg.fs24.entity.liases.exception.LiasRestIsNegative;
import com.kdg.fs24.lias.opers.attrs.*;
import java.time.LocalDate;
import java.util.Collection;
//import com.kdg.fs24.entity.liases.references.LiasesReferencesService;
import com.kdg.fs24.application.core.locale.NLS;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.api.ObjectRoot;
//import com.kdg.fs24.services.api.ServiceLocator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.lias.opers.api.LiasOpersConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.entity.liases.api.LiasRest;
import com.kdg.fs24.lias.opers.napi.LiasFinanceOper;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import javax.persistence.*;
import lombok.Data;
import com.kdg.fs24.references.liases.finopercode.LiasFinOperCode;
import com.kdg.fs24.references.liases.status.LiasOperStatus;
import com.kdg.fs24.references.liases.actiontype.LiasActionType;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "liases")
public class Lias extends ObjectRoot implements PersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lias_id")
    @SequenceGenerator(name = "seq_lias_id", sequenceName = "seq_lias_id", allocationSize = 1)
    @Column(name = "lias_id")
    private Integer liasId;
    //--------------------------------------------------------------------------
    @OneToOne
    @JoinColumn(name = "debt_id", referencedColumnName = "debt_id")
    private LiasDebt liasDebt;
    //--------------------------------------------------------------------------
    @Column(name = "start_date")
    private LocalDate startDate;
    //--------------------------------------------------------------------------
    @Column(name = "allow_date")
    private LocalDate allowDate;
    //--------------------------------------------------------------------------
    @Column(name = "final_date")
    private LocalDate finalDate;
    //--------------------------------------------------------------------------
    @Column(name = "legal_date")
    private LocalDate legalDate;
    //--------------------------------------------------------------------------
    @Column(name = "server_date")
    private LocalDateTime serverDate;
    //--------------------------------------------------------------------------
    @Column(name = "inactive_date")
    private LocalDate inactiveDate;
    //--------------------------------------------------------------------------
    @Column(name = "is_canceled")
    private Boolean isCancelled;

//    @OneToMany
//    @JoinColumn(name = "lias_id", referencedColumnName = "lias_id")
    @OneToMany(mappedBy = "lias",
            cascade = CascadeType.ALL)
    private Collection<LiasAction> liasActions;

    @OneToMany
    @JoinColumn(name = "lias_id", referencedColumnName = "lias_id")
    private Collection<LiasRest> liasRests;

    //==========================================================================
    // создание новой финоперации
    //==========================================================================
    public void createLiasOper(final BigDecimal liasSum,
            final LocalDate operDate,
            final Integer liasFinOperCode,
            final Integer liasTypeID,
            final Integer liasActionTypeId) {

        NullSafe.create(this.liasActions)
                .whenIsNull(() -> {
                    this.liasActions = ServiceFuncs.<LiasAction>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
                })
                .execute(() -> {

                    final LiasAction newLiasAction = NullSafe.createObject(LiasAction.class);

                    newLiasAction.setLias(this);
                    newLiasAction.setLiasSum(liasSum);
                    newLiasAction.setLiasFinOperCode(LiasFinOperCode.findLiasFinOperCode(liasFinOperCode));
                    newLiasAction.setLiasOperStatus(LiasOperStatus.findLiasOperStatus(1));
                    newLiasAction.setLiasDate(operDate);
                    newLiasAction.setLiasActionType(LiasActionType.findLiasActionType(liasActionTypeId));

//                    new LiasAction(
//                            SysConst.LONG_ZERO,
//                            SysConst.LONG_ZERO,
//                            this.getLiasId(),
//                            liasSum,
//                            operDate,
//                            LocalDateTime.now(),
//                            1,
//                            ServiceLocator.find(LiasesReferencesService.class).getLiasFinOperByCode(liasFinOperCode),
//                            ServiceLocator.find(LiasesReferencesService.class).getLiasActionTypeById(liasTypeID),
//                            liasOperHC,
//                            sah));
                    this.liasActions.add(newLiasAction);

                    //this.createOrUpdateLiasRests(liasSum, operDate);
                }).throwException();

        // изменение остатков по задолженности
        //==========================================================================
    }

//    public void createOrUpdateLiasRests(final NewLiasOper liasFinanceOper) {
//        this.createOrUpdateLiasRests(liasFinanceOper.<BigDecimal>attr(LiasOpersConst.LIAS_SUMM_CLASS),
//                liasFinanceOper.<LocalDate>attr(LiasOpersConst.LIAS_DATE_CLASS));
//    }
    public void createLiasOper(final LiasFinanceOper liasFinanceOper) {
        this.createLiasOper(liasFinanceOper.attr(LiasOpersConst.LIAS_SUMM_CLASS),
                liasFinanceOper.attr(LiasOpersConst.LIAS_DATE_CLASS),
                liasFinanceOper.attr(LiasOpersConst.LIAS_FINOPER_CODE_CLASS),
                liasFinanceOper.attr(LiasOpersConst.LIAS_TYPE_ID_CLASS),
                liasFinanceOper.attr(LiasOpersConst.LIAS_ACTION_TYPE_ID_CLASS));
    }

}
