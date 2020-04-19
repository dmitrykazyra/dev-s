/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.bond.schedule.references.api.PmtScheduleAlg;
import com.kdg.fs24.bond.schedule.references.api.PmtScheduleTerm;
import com.kdg.fs24.entity.core.api.CachedReferencClasses;
import com.kdg.fs24.entity.core.api.EntityClassesPackages;
import com.kdg.fs24.entity.kind.EntityKind;
import lombok.Data;
import org.springframework.stereotype.Service;
import com.kdg.fs24.retail.loan.contracts.RetailLoanContract;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.kdg.fs24.references.tariffs.api.TariffConst;
import com.kdg.fs24.entity.contract.subjects.ContractSubject;
import com.kdg.fs24.counterparties.api.Counterparty;
import com.kdg.fs24.loan.references.api.LoanSource;
import com.kdg.fs24.references.application.currency.Currency;
import com.kdg.fs24.tariff.core.api.TariffPlan;
import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 *
 * @author N76VB
 */
@Data
@Service
@EntityClassesPackages(pkgList = {"com.kdg.fs24.retail.loan.contracts"})
@CachedReferencClasses(classes = {ContractSubject.class, LoanSource.class, PmtScheduleAlg.class, PmtScheduleTerm.class})
public class RetailLoanContractActionsService extends ActionExecutionService {

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

        return this.<RetailLoanContract>createActionEntity(RetailLoanContract.class,
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
                    retailLoanContract.setLoanSource(loanSource);
                    retailLoanContract.setCreation_date(LocalDateTime.now());
                    retailLoanContract.setEntityStatus(this.getExistEntityStatus(TariffConst.ENTITY_TARIFF_PLAN, 0));
                });
    }
    //==========================================================================
    public ContractSubject getContractSubject(final Integer contractSubjectId) {

        return ServiceFuncs.getMapValue(this.getREF_CACHE(), mapEntry -> mapEntry.getKey().equals(ContractSubject.class))
                .get()
                .stream()
                .map(x -> (ContractSubject) x)
                .collect(Collectors.toList())
                .stream()
                .filter(contractSubject -> contractSubject.getContractSubjectId().equals(contractSubjectId))
                .findFirst()
                .get();
    }
}
