/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.nullsafe.StopWatcher;
import com.kdg.fs24.entity.core.api.EntityContractConst;
import lombok.Data;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import config.TestRLCConfig;
import org.junit.Test;
import com.kdg.fs24.entity.retail.loan.contracts.RetailLoanContract;
import com.kdg.fs24.entity.retail.loan.contracts.RetailLoanConstants;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author N76VB
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestRLCConfig.class)
//@DataJpaTest
@Data
public class TestRetailLoanContracts extends TestUtil4LoanContract {

    //final private Integer testEntityContractCurrency = 9999;
    @Test
    public void testRetailLoanContract() {
        //this.initializeTest();

        final RetailLoanContract retailLoanContract = this.createTestContract_1Y_840();
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
        this.getRetailLoanContractActionsService().executeAction(retailLoanContract, RetailLoanConstants.MODIFY_INDIVIDUAL_LOAN_CONTRACT);
        this.getRetailLoanContractActionsService().executeAction(retailLoanContract, EntityContractConst.ACT_AUTHORIZE_CONTRACT);
        this.getRetailLoanContractActionsService().executeAction(retailLoanContract, RetailLoanConstants.ACT_ISSUE_LOAN);
        this.getRetailLoanContractActionsService().executeAction(retailLoanContract, EntityContractConst.ACT_CALCULATE_TARIFFS);
//        this.getRetailLoanContractActionsService().executeAction(retailLoanContract, EntityContractConst.ACT_FINISH_CONTRACT);

        final Long entityId = retailLoanContract.getEntity_id();
        //final Long entityId = Long.valueOf(70533);

        this.getPersistanceEntityManager()
                .getEntityManager()
                .clear();

        final StopWatcher stopWatcher = StopWatcher.create();

        LogService.LogInfo(this.getClass(), () -> String.format("try 2 reload created entity (%d)",
                entityId));

        // поиск сущности
        final RetailLoanContract entity = this.getPersistanceEntityManager()
                .getEntityManager()
                .find(RetailLoanContract.class, Long.valueOf(entityId));

//        this.getPersistanceEntityManager()
//                .getEntityManager()
//                .refresh(entity);
        LogService.LogInfo(this.getClass(), () -> String.format("Refresh entity is finished (%d, %d ms)",
                entityId,
                stopWatcher.getTimeExecMillis()));

//        persistanceEntityManager
//                .getEntityManager()
//                .refresh(retailLoanContract);
    }
}
