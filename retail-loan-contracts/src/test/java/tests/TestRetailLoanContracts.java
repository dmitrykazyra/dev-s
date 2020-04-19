/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.TestFuncs;
import com.kdg.fs24.bond.schedule.references.api.PmtScheduleAlg;
import com.kdg.fs24.bond.schedule.references.api.PmtScheduleTerm;
import com.kdg.fs24.counterparties.api.Counterparty;
import com.kdg.fs24.entity.contract.subjects.ContractSubject;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import lombok.Data;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import config.TestRLCConfig;
import com.kdg.fs24.service.RetailLoanContractActionsService;
import com.kdg.fs24.entity.kind.EntityKind;
import com.kdg.fs24.loan.references.api.LoanSource;
import com.kdg.fs24.references.application.currency.Currency;
import org.junit.Test;
import com.kdg.fs24.retail.loan.contracts.RetailLoanContract;
import com.kdg.fs24.retail.loan.contracts.RetailLoanConstants;
import com.kdg.fs24.tariff.core.api.TariffPlan;
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

    //final private Integer testEntityContractCurrency = 9999;
    @Test
    public void testRetailLoanContract() {
        //this.initializeTest();

        final String testString = TestFuncs.generateTestString20();
        final Integer kindId = RetailLoanConstants.LOAN2INDIVIDUAL_CARD;

        final EntityKind entityKind = this.retailLoanContractActionsService.getExistEntityKind(kindId);

        if (NullSafe.isNull(entityKind)) {
            throw new RuntimeException(String.format("EntityKind is not found(%d)", kindId));
        }

        final ContractSubject contractSubject = this.retailLoanContractActionsService.getContractSubject(0);
        
//        final RetailLoanContract retailLoanContract = retailLoanContractActionsService
//                .createRetailLoanContract(testString, testString, entityKind, LocalDate.now(), LocalDate.now());
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

//        retailLoanContractActionsService.executeAction(retailLoanContract, RetailLoanConstants.MODIFY_INDIVIDUAL_LOAN_CONTRACT);
        //retailLoanContractActionsService.executeAction(retailLoanContract, TariffConst.ACT_AUTHORIZE_TARIFF_PLAN);

    }
}
