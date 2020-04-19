/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.TestFuncs;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import com.kdg.fs24.references.application.currency.Currency;
import lombok.Data;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import config.TestReferencesConfig;
import com.kdg.fs24.service.TariffCoreActionsService;
import com.kdg.fs24.entity.kind.EntityKind;
import org.junit.Test;
import com.kdg.fs24.tariff.core.AbstractTariffPlan;
import com.kdg.fs24.references.tariffs.api.TariffConst;
import java.time.LocalDate;

/**
 *
 * @author N76VB
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestReferencesConfig.class)
//@DataJpaTest
@Data
public class TestTariffCore {

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    @Autowired
    private TariffCoreActionsService tariffCoreActionsService;

    //final private Integer testEntityContractCurrency = 9999;
    @Test
    public void testTariffPlan() {
        //this.initializeTest();

        final String testString = TestFuncs.generateTestString20();
        final Integer kindId = TariffConst.EK_TP_FOR_RETAIL_LOAN_CONTRACT;

        final EntityKind entityKind = persistanceEntityManager
                .getEntityManager()
                .find(EntityKind.class, kindId);

        if (NullSafe.isNull(entityKind)) {
            throw new RuntimeException(String.format("EntityKind is not found(%d)", kindId));
        }

        final AbstractTariffPlan tariffPlanImpl = tariffCoreActionsService
                .createTariffPlan(testString, testString, entityKind, LocalDate.now(), LocalDate.now());

        tariffCoreActionsService.executeAction(tariffPlanImpl, TariffConst.ACT_MODIFY_TARIFF_PLAN);
        tariffCoreActionsService.executeAction(tariffPlanImpl, TariffConst.ACT_AUTHORIZE_TARIFF_PLAN);

    }
}
