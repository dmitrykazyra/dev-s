/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.debts;

import com.kdg.fs24.references.application.currency.Currency;
import com.kdg.fs24.references.liases.baseassettype.LiasBaseAssetType;
import com.kdg.fs24.references.liases.debtstate.LiasDebtState;
import com.kdg.fs24.references.liases.kind.LiasKind;
import com.kdg.fs24.references.liases.type.LiasType;
//import com.kdg.fs24.entity.bondschedule.PmtSchedule;
import com.kdg.fs24.lias.opers.api.LiasOpersConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import java.time.LocalDate;
import java.util.Collection;
import com.kdg.fs24.application.core.api.ObjectRoot;
import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import com.kdg.fs24.lias.opers.napi.LiasFinanceOper;
import java.math.BigDecimal;
import com.kdg.fs24.lias.opers.attrs.*;
import javax.persistence.*;
import lombok.Data;
import java.util.Optional;
import com.kdg.fs24.application.core.service.funcs.FilterComparator;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import com.kdg.fs24.entity.counterparties.api.Counterparty;
import com.kdg.fs24.entity.contracts.AbstractEntityContract;
import com.kdg.fs24.entity.bondschedule.PmtSchedule;
import com.kdg.fs24.service.LiasDocumentBuilders;
import com.kdg.fs24.spring.core.api.ServiceLocator;

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
    //--------------------------------------------------------------------------
    @OneToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id")
    private AbstractEntityContract debtContract;
    //--------------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "counterparty_id", referencedColumnName = "counterparty_id")
    private Counterparty counterparty;
    //--------------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
    private Currency currency;
    //--------------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "debt_state_id", referencedColumnName = "debt_state_id")
    private LiasDebtState liasDebtState;
    //--------------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "lias_kind_id", referencedColumnName = "lias_kind_id")
    private LiasKind liasKind;
    //--------------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "lias_type_id", referencedColumnName = "lias_type_id")
    private LiasType liasType;
    //--------------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "base_asset_type_id", referencedColumnName = "base_asset_type_id")
    private LiasBaseAssetType liasBaseAssetType;
    //--------------------------------------------------------------------------
    @Column(name = "debt_start_date")
    private LocalDate debtStartDate;
    //--------------------------------------------------------------------------
    @Column(name = "debt_final_date")
    private LocalDate debtFinalDate;
    //--------------------------------------------------------------------------
    //@OneToMany
    //@JoinColumn(name = "debt_id", referencedColumnName = "debt_id")
    @OneToMany(mappedBy = "liasDebt",
            cascade = CascadeType.ALL)
    private Collection<Lias> liases;
//    //--------------------------------------------------------------------------
//    @OneToMany
//    @JoinColumn(name = "debt_id", referencedColumnName = "debt_id")
//    private Collection<LiasDebtRest> debtRests;

    //==========================================================================
    // сервисная часть
    //==========================================================================
    public final void createOrUpdateLiases(final LiasFinanceOper liasFinanceOper) {

        // график погашения обязательства
        final Optional<PmtSchedule> pmtSchedule = ServiceFuncs.<PmtSchedule>getCollectionElement(debtContract.getPmtSchedules(),
                bs -> (bs.getEntityKind().getEntityKindId().equals(liasFinanceOper.<Integer>attr(PMT_SCHEDULE.class))));
        
        if (NullSafe.isNull(this.getLiases())) {
            this.setLiases(ServiceFuncs.<Lias>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL));
        }
        if (pmtSchedule.isPresent()) {
            // обязательства формируются согласно графика
            this.processBondSchedLiases(liasFinanceOper, pmtSchedule.get());
        } else {
            // обязательство без графика
            this.processLias(liasFinanceOper);
        }

//        NullSafe.create(liasFinanceOper.<PmtSchedule>attr(PMT_SCHEDULE.class))
//                .inititialize(() -> this.liases = ServiceFuncs.<Lias>getOrCreateCollection(this.liases))
//                .safeExecute(() -> {
//                    // обязательства формируются согласно графика
//                    this.processBondSchedLiases(liasFinanceOper);
//                })
//                .whenIsNull(() -> {
//                    // обязательство без графика
//                    this.processLias(liasFinanceOper);
//                });
    }

    //==========================================================================
    private void processBondSchedLiases(final LiasFinanceOper liasFinanceOper, final PmtSchedule pmtSchedule) {

        //----------------------------------------------------------------------
        // увеличение обязательств
        if (liasFinanceOper.<BigDecimal>attr(LiasOpersConst.LIAS_SUMM_CLASS).signum() > 0) {
            this.incrementLiases(liasFinanceOper, pmtSchedule);
        } else {
            // уменьшение обязательств
            this.decrementLiases(liasFinanceOper);
        }
    }

    // обязательство без графика
    //==========================================================================
    private void processLias(final LiasFinanceOper liasFinanceOper) {
        
        if (liasFinanceOper.<BigDecimal>attr(LiasOpersConst.LIAS_SUMM_CLASS).signum() > 0) {
            Lias lias = this.findLias(liasFinanceOper.<LocalDate>attr(LiasOpersConst.LIAS_START_DATE_CLASS),
                    liasFinanceOper.<LocalDate>attr(LiasOpersConst.LIAS_FINAL_DATE_CLASS),
                    liasFinanceOper.<BigDecimal>attr(LiasOpersConst.LIAS_SUMM_CLASS).signum() < 0);

            // если обязательство не найдено, то создаем его
            lias = NullSafe.create(lias)
                    .whenIsNull(() -> {
                        // обязательство не найдено
                        // графика нет - формируем одно обязательство
                        final Lias newLias = this.createLias(liasFinanceOper);
                        this.liases.add(newLias);
                        return newLias;
                    }).<Lias>getObject();

            // создаем действие по обязательству
            lias.createLiasOper(liasFinanceOper);
        } else {
            //this.findFirstLias().createLiasOper(liasFinanceOper);
            this.decrementLiases(liasFinanceOper);
        }
    }

    //==========================================================================
    public Lias findLias(
            LocalDate startDate,
            LocalDate finalDate,
            Boolean throwExcWhenNotFound) {
        
        final FilterComparator<Lias> filterComparator = l -> (l.getStartDate().equals(startDate) && l.getFinalDate().equals(finalDate));
        final Optional<Lias> lias = ServiceFuncs.<Lias>getCollectionElement(this.getLiases(),
                filterComparator);
        
        if (throwExcWhenNotFound && !lias.isPresent()) {
            
            class LiasDoesNotExists extends InternalAppException {
                
                public LiasDoesNotExists(final String message) {
                    super(message);
                }
            }
            throw new LiasDoesNotExists(String.format("Обязательство не существует(%s,%s)", startDate, finalDate));
        }
        
        return lias.orElse(null);
    }
    
    interface LiasOperRest {
        
        BigDecimal getLiasOperSum();
    }

    //==========================================================================
    private void incrementLiases(final LiasFinanceOper liasFinanceOper, final PmtSchedule pmtSchedule) {
        //----------------------------------------------------------------------

        // сумма операции
        //анонимный класс для вычисления остатка
        final LiasOperRest liasOperRest = new LiasOperRest() {
            
            private BigDecimal liasOperRest = (BigDecimal) liasFinanceOper.<BigDecimal>attr(LiasOpersConst.LIAS_SUMM_CLASS);
            final private BigDecimal substrSum = liasOperRest.divide(BigDecimal.valueOf(pmtSchedule.getPmtScheduleLines().size()), 2, 2);
            
            @Override
            public BigDecimal getLiasOperSum() {
                final BigDecimal liasOperSum = liasOperRest.min(substrSum);
                liasOperRest = liasOperRest.subtract(substrSum);
                return liasOperSum;
            }
        };
        
        final LiasDocumentBuilders db = ServiceLocator.<LiasDocumentBuilders>findService(LiasDocumentBuilders.class);
        
        pmtSchedule
                .getPmtScheduleLines()
                .stream()
                .forEach((pmtScheduleLine) -> {
                    
                    final Lias schedLias = NullSafe.create(this.findLias(pmtScheduleLine.getFromDate(),
                            pmtScheduleLine.getToDate(),
                            (liasFinanceOper.<BigDecimal>attr(LiasOpersConst.LIAS_SUMM_CLASS)).signum() < 0))
                            .whenIsNull(() -> this.createLias(pmtScheduleLine.getFromDate(),
                            pmtScheduleLine.getToDate())
                            ).<Lias>getObject();
                    schedLias.setLiasDebt(this);
                    liases.add(schedLias);

                    // создание финопераций
                    schedLias.createLiasOper(liasOperRest.getLiasOperSum(),
                            liasFinanceOper.<LocalDate>attr(LiasOpersConst.LIAS_DATE_CLASS),
                            liasFinanceOper.<Integer>attr(LiasOpersConst.LIAS_FINOPER_CODE_CLASS),
                            liasFinanceOper.<Integer>attr(LIAS_TYPE_ID.class),
                            liasFinanceOper.<Integer>attr(LiasOpersConst.LIAS_ACTION_TYPE_ID_CLASS),
                            db.createDocument((doc) -> {
                                doc.setEntity(this.getDebtContract());
                            }, liasFinanceOper));
                    
                });
        
    }

    //==========================================================================
    // создание нового обязательства
    private Lias createLias(final LiasFinanceOper liasFinanceOper) {
        //LogService.LogInfo(this.getClass(), LogService.getCurrentObjProcName(this));
        final Lias lias = NullSafe.createObject(Lias.class);
        
        lias.setStartDate(liasFinanceOper.<LocalDate>attr(LiasOpersConst.LIAS_START_DATE_CLASS));
        lias.setAllowDate(liasFinanceOper.<LocalDate>attr(LiasOpersConst.LIAS_START_DATE_CLASS));
        lias.setFinalDate(liasFinanceOper.<LocalDate>attr(LiasOpersConst.LIAS_FINAL_DATE_CLASS));
        lias.setLegalDate(liasFinanceOper.<LocalDate>attr(LiasOpersConst.LIAS_START_DATE_CLASS));
        lias.setServerDate(LocalDateTime.now());
        lias.setIsCancelled(Boolean.FALSE);
        
        return lias;
        
    }

    //==========================================================================
    private Lias createLias(final LocalDate liasStartDate, final LocalDate liasFinalDate) {
        //LogService.LogInfo(this.getClass(), LogService.getCurrentObjProcName(this));

        final Lias lias = NullSafe.createObject(Lias.class);
        
        lias.setStartDate(liasStartDate);
        lias.setAllowDate(liasStartDate);
        lias.setFinalDate(liasFinalDate);
        lias.setLegalDate(liasFinalDate);
        lias.setServerDate(LocalDateTime.now());
        lias.setIsCancelled(Boolean.FALSE);
        
        return lias;
        
    }

    //==========================================================================  
    public Lias findFirstLias() {
        
        class LiasNotFound extends InternalAppException {
            
            public LiasNotFound(final String message) {
                super(message);
            }
        }
        
        final FilterComparator<Lias> filterComparator = l -> ((NullSafe.isNull(l.getInactiveDate())));
        final Optional<Lias> lias = ServiceFuncs.<Lias>getCollectionElement(
                this.getLiases()
                        .stream()
                        .sorted((lias1, lias2) -> lias1.getStartDate().compareTo(lias2.getStartDate()))
                        .collect(Collectors.toList()),
                filterComparator);
        
        return lias.orElseThrow(() -> new LiasNotFound(String.format("Не найдено подходящее обязательство (DebtId=%d)", this.debtId)));

//        return ServiceFuncs.<L>getCollectionElement(this.getLiases(),
//                (l -> ((NullSafe.isNull(l.getInactive_date())))),
//                (lias1, lias2) -> {
//                    // сортировка по getStart_date
//                    return lias1.getStart_date().compareTo(lias2.getStart_date());
//                },
//                String.format("Не найдено подходящее обязательство (DebtId=%d)", this.debtId),
//                ServiceFuncs.THROW_WHEN_NOT_FOUND);
    }

    //==========================================================================
    private void decrementLiases(final LiasFinanceOper liasFinanceOper) {
        NullSafe.create()
                .execute(() -> {
                    // ищем обязаетельство для его уменьшения
                    this.findFirstLias().createLiasOper(liasFinanceOper);
                });
    }

    //==========================================================================
    public final void createOrUpdateDebtRests(final LiasFinanceOper liasFinanceOper) {
        
    }
    
}
