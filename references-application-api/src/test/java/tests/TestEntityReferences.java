/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

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
import com.kdg.fs24.service.ApplicationReferencesService;
import org.junit.Test;

/**
 *
 * @author N76VB
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestReferencesConfig.class)
//@DataJpaTest
@Data
public class TestEntityReferences {
    
    @Autowired
    private PersistanceEntityManager persistanceEntityManager;
    
    @Autowired
    private ApplicationReferencesService applicationReferencesService;
    
    final private Integer testEntityContractCurrency = 9999;
    
    @Test
    public void testCurrencies() {
        //this.initializeTest();

        String testString = TestFuncs.generateTestString20();
        
        applicationReferencesService.createCurrency(testEntityContractCurrency, testString.substring(1, 3), testString);
        
        persistanceEntityManager
                .getEntityManager()
                .remove(persistanceEntityManager
                        .getEntityManager()
                        .find(Currency.class, testEntityContractCurrency));
        
    }
}
