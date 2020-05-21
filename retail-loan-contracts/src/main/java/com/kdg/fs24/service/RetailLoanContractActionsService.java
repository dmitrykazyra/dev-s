/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.references.bond.schedule.api.PmtScheduleAlg;
import com.kdg.fs24.references.bond.schedule.api.PmtScheduleTerm;
import com.kdg.fs24.entity.core.api.EntityClassesPackages;
import com.kdg.fs24.entity.kind.EntityKind;
import com.kdg.fs24.entity.status.EntityStatus;
import lombok.Data;
import org.springframework.stereotype.Service;
import com.kdg.fs24.entity.retail.loan.contracts.RetailLoanContract;
import java.time.LocalDate;
import com.kdg.fs24.references.liases.debtstate.LiasDebtState;
import com.kdg.fs24.references.liases.kind.LiasKind;
import com.kdg.fs24.references.liases.type.LiasType;
import com.kdg.fs24.references.liases.baseassettype.LiasBaseAssetType;
import com.kdg.fs24.references.liases.finopercode.LiasFinOperCode;
import com.kdg.fs24.references.liases.status.LiasOperStatus;
import com.kdg.fs24.entity.contract.subjects.ContractSubject;
import com.kdg.fs24.entity.counterparties.api.Counterparty;
import com.kdg.fs24.references.loan.api.LoanSource;
import com.kdg.fs24.references.application.currency.Currency;
import com.kdg.fs24.entity.tariff.api.TariffPlan;
import java.math.BigDecimal;
import com.kdg.fs24.entity.core.api.CachedReferencesClasses;
import com.kdg.fs24.entity.retail.loan.contracts.RetailLoanConstants;
import com.kdg.fs24.references.liases.actiontype.LiasActionType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author N76VB
 */
@Data
@Service
@EntityClassesPackages(pkgList = {"com.kdg.fs24.entity"})
@CachedReferencesClasses(classes = {ContractSubject.class, LoanSource.class, PmtScheduleAlg.class,
    PmtScheduleTerm.class, Currency.class, LiasDebtState.class, LiasKind.class, LiasType.class, 
    LiasBaseAssetType.class, LiasFinOperCode.class, LiasOperStatus.class, LiasActionType.class})
public class RetailLoanContractActionsService extends ActionExecutionService {

    @Autowired
    private ContractSchedulesBuilders contractSchedulesBuilders;

    public RetailLoanContract createRetailLoanContract(final ContractSubject contractSubject,
            final Counterparty counterparty,
            final EntityKind entityKind,
            final Currency currency,
            final TariffPlan tariffPlan,
            final String contractNum,
            final LocalDate contractDate,
            final LocalDate beginDate,
            final LocalDate endDate,
            final BigDecimal contractSumm,
            final LoanSource loanSource,
            final PmtScheduleAlg pmtScheduleAlg,
            final PmtScheduleTerm pmtScheduleTerm) {

        final RetailLoanContract rlc = this.<RetailLoanContract>createActionEntity(RetailLoanContract.class,
                (retailLoanContract) -> {
                    retailLoanContract.setContractSubject(contractSubject);
                    retailLoanContract.setCounterparty(counterparty);
                    retailLoanContract.setCurrency(currency);
                    retailLoanContract.setTariffPlan(tariffPlan);
                    retailLoanContract.setContractNum(contractNum);
                    retailLoanContract.setContractDate(contractDate);
                    retailLoanContract.setBeginDate(beginDate);
                    retailLoanContract.setEndDate(endDate);
                    retailLoanContract.setPmtScheduleAlg(pmtScheduleAlg);
                    retailLoanContract.setPmtScheduleTerm(pmtScheduleTerm);
                    retailLoanContract.setEntityKind(entityKind);
                    retailLoanContract.setContractSumm(contractSumm);
                    retailLoanContract.setLoanSource(loanSource);
                    //retailLoanContract.setCreation_date(LocalDateTime.now());
                    retailLoanContract.setEntityStatus(EntityStatus.findEntityStatus(RetailLoanConstants.LOAN2INDIVIDUAL, 0));
                    //EntityStatus.getExistEntityStatus(TariffConst.ENTITY_TARIFF_PLAN, 0));
                    // построить графики погашения
                    //retailLoanContract.createBondschedules();
                });

        return rlc;
    }
}
