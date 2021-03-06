package tests;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.TestFuncs;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.bond.schedule.api.BondScheduleConst;
import com.kdg.fs24.entity.contract.subjects.ContractSubject;
import com.kdg.fs24.entity.counterparties.api.Counterparty;
import com.kdg.fs24.entity.kind.EntityKind;
import com.kdg.fs24.entity.retail.loan.contracts.RetailLoanConstants;
import com.kdg.fs24.entity.retail.loan.contracts.RetailLoanContract;
import com.kdg.fs24.entity.tariff.AbstractTariffPlan;
import com.kdg.fs24.entity.tariff.TariffRate_1;
import com.kdg.fs24.entity.tariff.AbstractTariffPlan;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import com.kdg.fs24.references.application.currency.Currency;
import com.kdg.fs24.references.bond.schedule.api.PmtScheduleAlg;
import com.kdg.fs24.references.bond.schedule.api.PmtScheduleTerm;
import com.kdg.fs24.references.loan.api.LoanSource;
import com.kdg.fs24.references.tariffs.accretionscheme.TariffAccretionScheme;
import com.kdg.fs24.references.tariffs.api.TariffConst;
import com.kdg.fs24.references.tariffs.kind.TariffKind;
import com.kdg.fs24.service.CounterpartyActionsService;
import com.kdg.fs24.service.RetailLoanContractActionsService;
import com.kdg.fs24.service.TariffCoreActionsService;
import com.kdg.fs24.test.core.Utils4test;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public abstract class TestUtil4LoanContract extends Utils4test {

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    @Autowired
    private RetailLoanContractActionsService retailLoanContractActionsService;

    @Autowired
    private CounterpartyActionsService counterpartyActionsService;

    @Autowired
    private TariffCoreActionsService tariffCoreActionsService;

    //==========================================================================
    protected RetailLoanContract createTestContract_1Y_840() {
        final String testString = TestFuncs.generateTestString20();

        final ContractSubject contractSubject = ContractSubject.findContractSubject(210001);

        final EntityKind entityKind = EntityKind.findEntityKind(RetailLoanConstants.LOAN2INDIVIDUAL_CARD);

        final Counterparty counterparty = this.getCounterpartyActionsService().createCounterparty(testString, testString, testString);

        final Currency currency = Currency.findCurrency(840);

        final AbstractTariffPlan tariffPlan = this.createTestTariffPlan_rate_1(); //this.getRetailLoanContractActionsService().<AbstractTariffPlan>findActionEntity(AbstractTariffPlan.class, Long.valueOf(68338)).get();
        final String contractNum = testString;
        final LocalDate contractDate = LocalDate.now();
        final LocalDate beginDate = LocalDate.now();
        final LocalDate endDate = beginDate.plusYears(1);
        final BigDecimal contractSumm = SysConst.MAX_BIGDECIMAL;
        final LoanSource loanSource = LoanSource.findLoanSource(102);
        // код графика выплат ОД
        final PmtScheduleAlg pmtScheduleAlg = PmtScheduleAlg.findLoanSource(BondScheduleConst.BS_ALG_BYREST);
        // срок
        final PmtScheduleTerm pmtScheduleTerm = PmtScheduleTerm.findPmtScheduleTerm(30);

        return this.getRetailLoanContractActionsService()
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
    }

    //==========================================================================
    protected AbstractTariffPlan createTestTariffPlan_rate_1() {
        final String testString = TestFuncs.generateTestString20();
        final Integer kindId = TariffConst.EK_TP_FOR_RETAIL_LOAN_CONTRACT;

//        final EntityKind entityKind = persistanceEntityManager
//                .getEntityManager()
//                .find(EntityKind.class, kindId);
        final EntityKind entityKind = EntityKind.findEntityKind(kindId);

        if (NullSafe.isNull(entityKind)) {
            throw new RuntimeException(String.format("EntityKind is not found(%d)", kindId));
        }

        final AbstractTariffPlan tariffPlanImpl = tariffCoreActionsService
                .createTariffPlan(testString, testString, entityKind, LocalDate.now(), LocalDate.now(),
                        (plan) -> {

                            plan.addServKindId(TariffKind.findTariffKind(TariffConst.TK_CURRENT_RESTS),
                                    LocalDate.now(),
                                    SysConst.LOCALDATE_NULL,
                                    (tariffPlan2Serv) -> {
                                        // добавление услуги в тарифный план
                                        tariffPlan2Serv.addTariffRate(TariffAccretionScheme.findTariffAccretionScheme(1),
                                                TariffKind.findTariffKind(TariffConst.TK_CURRENT_RESTS), LocalDate.now(), SysConst.LOCALDATE_NULL, testString,
                                                (tariffRate_1) -> {

                                                    final LocalDate ld = LocalDate.now();

                                                    tariffRate_1.addTariffRate_1(ld.minusDays(50), BigDecimal.valueOf(1.9), Currency.findCurrency(840));
                                                    tariffRate_1.addTariffRate_1(ld.minusDays(40), BigDecimal.valueOf(2.9), Currency.findCurrency(840));
                                                    tariffRate_1.addTariffRate_1(ld, BigDecimal.valueOf(3.3), Currency.findCurrency(840));
                                                    tariffRate_1.addTariffRate_1(ld.plusDays(30), BigDecimal.valueOf(3.9), Currency.findCurrency(840));
                                                    tariffRate_1.addTariffRate_1(ld.plusDays(40), BigDecimal.valueOf(4.9), Currency.findCurrency(840));
                                                    tariffRate_1.addTariffRate_1(ld.plusDays(60), BigDecimal.valueOf(5.9), Currency.findCurrency(840));

                                                });
                                    });
                        });

        tariffCoreActionsService.executeAction(tariffPlanImpl, TariffConst.ACT_MODIFY_TARIFF_PLAN);
        tariffCoreActionsService.executeAction(tariffPlanImpl, TariffConst.ACT_AUTHORIZE_TARIFF_PLAN);

        return tariffPlanImpl;
    }

}
