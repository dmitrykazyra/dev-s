/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.kdg.fs24.application.core.log.LogService;
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

        final Long entityId = retailLoanContract.getEntity_id();

        LogService.LogInfo(this.getClass(), () -> String.format("try 2 refresh created entity (%d)",
                entityId));

        // поиск сущности
        final RetailLoanContract entity = this.getPersistanceEntityManager()
                .getEntityManager()
                .find(RetailLoanContract.class, Long.valueOf(entityId));

        this.getPersistanceEntityManager()
                .getEntityManager()
                .refresh(entity);
        LogService.LogInfo(this.getClass(), () -> String.format("Refresh entity is finished (%d)",
                entityId));

        final Integer i1 = entity.getEntityMarks().size();
        final Integer i2 = entity.getEntityActions().size();

        entity.getPmtSchedules().stream().forEach((schedule) -> {

            LogService.LogInfo(this.getClass(), () -> String.format("entity.getPmtSchedules(%s).size() =  (%d) records",
                    schedule.getEntityKind().getEntityKindName(),
                    schedule.getPmtScheduleLines().size()));
        });

//        persistanceEntityManager
//                .getEntityManager()
//                .refresh(retailLoanContract);
    }
}
