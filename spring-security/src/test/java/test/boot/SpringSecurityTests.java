/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.boot;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import com.kdg.fs24.spring.security.api.ApplicationUser;
import com.kdg.fs24.spring.security.ApplicationUserImpl;
import test.config.SpringSecurityTestConfig;
import com.kdg.fs24.spring.unit.Unit4Test;
import java.time.LocalDateTime;
import org.hibernate.Session;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import org.springframework.context.annotation.Import;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import com.kdg.fs24.repository.EntityTypesRepository;


/**
 *
 * @author N76VB
 */
//public class SpringSecurityTests extends SpringBoot4Test<SpringSecurityTestConfig> {

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SpringSecurityTestConfig.class)
public final class SpringSecurityTests extends Unit4Test<SpringSecurityBoot, SpringSecurityTestConfig> {

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;
    
    @Autowired
    private EntityTypesRepository entityTypesRepository;

    @Test
    public void test1() {
        //this.initializeTest();
        LogService.LogInfo(this.getClass(), () -> String.format("Unit test '%s' is running ",
                this.getClass().getCanonicalName()));
        persistanceEntityManager
                .executeTransaction((entityManager) -> {

//                    entityManager
//                            .unwrap(Session.class)
//                            .setJdbcBatchSize(Math.min(100, collection.size()));
                    // создание тестового пользователя
                    final ApplicationUserImpl user = NullSafe.createObject(ApplicationUserImpl.class);

                    final String testUserName = UUID.randomUUID().toString();

                    user.setName(testUserName);
                    user.setMail("testmai@nomail.com");
                    user.setPhone(testUserName);
                    user.setPassword(testUserName);
                    
                    user.setEntityType(entityTypesRepository.getOne(100));

                    entityManager.persist(user);

                });

    }

//    @Test
//    public void testXX() {
//        //this.initializeTest();
//        LogService.LogInfo(this.getClass(), () -> String.format("Unit test '%s' is running ",
//                this.getClass().getCanonicalName()));
//    }
}
