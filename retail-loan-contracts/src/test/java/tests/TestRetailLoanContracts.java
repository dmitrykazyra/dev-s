/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.TestFuncs;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.bond.schedule.references.api.PmtScheduleAlg;
import com.kdg.fs24.bond.schedule.references.api.PmtScheduleTerm;
import com.kdg.fs24.counterparties.api.Counterparty;
import com.kdg.fs24.entity.contract.subjects.ContractSubject;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.entity.core.api.EntityContractConst;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import lombok.Data;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import config.TestRLCConfig;
import com.kdg.fs24.service.RetailLoanContractActionsService;
import com.kdg.fs24.service.CounterpartyActionsService;
import com.kdg.fs24.entity.kind.EntityKind;
import com.kdg.fs24.loan.references.api.LoanSource;
import com.kdg.fs24.references.api.AbstractRefRecord;
import com.kdg.fs24.references.application.currency.Currency;
import org.junit.Test;
import com.kdg.fs24.retail.loan.contracts.RetailLoanContract;
import com.kdg.fs24.retail.loan.contracts.RetailLoanConstants;
import com.kdg.fs24.tariff.core.api.TariffPlan;
import com.kdg.fs24.tariff.core.AbstractTariffPlan;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author N76VB
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestRLCConfig.class)
//@DataJpaTest
@Data
public class TestRetailLoanContracts {

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    @Autowired
    private RetailLoanContractActionsService retailLoanContractActionsService;

    @Autowired
    private CounterpartyActionsService counterpartyActionsService;

    //final private Integer testEntityContractCurrency = 9999;
    @Test
    public void testRetailLoanContract() {
        //this.initializeTest();

        final String testString = TestFuncs.generateTestString20();
        final Integer kindId = RetailLoanConstants.LOAN2INDIVIDUAL_CARD;

        final ContractSubject contractSubject
                = AbstractRefRecord.<ContractSubject>getRefeenceRecord(
                        ContractSubject.class,
                        record -> record.getContractSubjectId().equals(210001));

        final EntityKind entityKind = AbstractRefRecord.<EntityKind>getRefeenceRecord(
                EntityKind.class,
                record -> record.getEntityKindId().equals(RetailLoanConstants.LOAN2INDIVIDUAL_CARD));

        final Counterparty counterparty = this.counterpartyActionsService.createCounterparty(testString, testString, testString);

        final Currency currency
                = AbstractRefRecord.<Currency>getRefeenceRecord(
                        Currency.class,
                        record -> record.getCurrencyId().equals(840));

        final TariffPlan tariffPlan = retailLoanContractActionsService.<AbstractTariffPlan>findActionEntity(AbstractTariffPlan.class, Long.valueOf(68338)).get();
        final String contractNum = testString;
        final LocalDate contractDate = LocalDate.now();
        final LocalDate beginDate = LocalDate.now();
        final LocalDate endDate = LocalDate.now();
        final BigDecimal contractSumm = SysConst.MAX_BIGDECIMAL;
        final LoanSource loanSource
                = AbstractRefRecord.<LoanSource>getRefeenceRecord(
                        LoanSource.class,
                        record -> record.getLoanSourceId().equals(102));
        final PmtScheduleAlg pmtScheduleAlg
                = AbstractRefRecord.<PmtScheduleAlg>getRefeenceRecord(
                        PmtScheduleAlg.class,
                        record -> record.getScheduleAlgId().equals(1));
        final PmtScheduleTerm pmtScheduleTerm
                = AbstractRefRecord.<PmtScheduleTerm>getRefeenceRecord(
                        PmtScheduleTerm.class,
                        record -> record.getPmtTermId().equals(30));

        final RetailLoanContract retailLoanContract = retailLoanContractActionsService
                .createRetailLoanContract(contractSubject,
                        counterparty,
                        entityKind,
                        currency,
                        tariffPlan,
                        contractNum,
                        contractDate,
                        beginDate,
                        endDate,
                        contractSumm,
                        loanSource,
                        pmtScheduleAlg,
                        pmtScheduleTerm);
//        
//        
//            final Counterparty counterparty,
//            final EntityKind entityKind,
//            final Currency currency,
//            final TariffPlan tariffPlan,
//            final String contractNum,
//            final LocalDate contractDate,
//            final LocalDate beginDate,
//            final LocalDate endDate,
//            final BigDecimal contractSumm,
//            final LoanSource loanSource,
//            final PmtScheduleAlg pmtScheduleAlg,
//            final PmtScheduleTerm pmtScheduleTerm
        retailLoanContractActionsService.executeAction(retailLoanContract, RetailLoanConstants.MODIFY_INDIVIDUAL_LOAN_CONTRACT);
        retailLoanContractActionsService.executeAction(retailLoanContract, EntityContractConst.ACT_AUTHORIZE_CONTRACT);

        final Long entityId = retailLoanContract.getEntity_id();

            // поиск сущности
            final RetailLoanContract entity = persistanceEntityManager
                    .getEntityManager()
                    .find(RetailLoanContract.class, Long.valueOf(68833));        
        
        final Integer i = entity.getEntityMarks().size();
        final Integer i1 = entity.getEntityActions().size();

//        persistanceEntityManager
//                .getEntityManager()
//                .refresh(retailLoanContract);
    }
}
