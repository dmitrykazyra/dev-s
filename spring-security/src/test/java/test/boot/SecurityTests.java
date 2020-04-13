/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.boot;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.entity.status.EntityStatusPK;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import com.kdg.fs24.spring.security.ApplicationUserImpl;
import com.kdg.fs24.spring.security.ApplicationRoleImpl;
import test.config.SecurityTestConfig;
import java.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import org.springframework.context.annotation.Import;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import com.kdg.fs24.repository.*;
import com.kdg.fs24.repository.EntityStatusesRepository;
import lombok.Data;

/**
 *
 * @author N76VB
 */
//public class SpringSecurityTests extends SpringBoot4Test<SpringSecurityTestConfig> {
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SecurityTestConfig.class)
//@DataJpaTest
@Data
//public final class SpringSecurityTests extends Unit4Test<SpringSecurityBoot, SecurityTestConfig> {
public class SecurityTests {

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;
//
//    @Autowired(required = false)
//    private EntityStatusesRepository entityStatusesRepository;

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

                    final String testValue = UUID.randomUUID().toString().substring(1, 20);

                    user.setName(testValue);
                    user.setMail("testmai@nomail.com");
                    user.setPhone(testValue);
                    user.setPassword(testValue);
                    user.setCreation_date(LocalDateTime.now());
                    user.setLogin(testValue);

                    //==========================================================
                    final EntityStatusPK entityStatusPK = NullSafe.createObject(EntityStatusPK.class);

                    entityStatusPK.setEntityStatusId(1);
                    entityStatusPK.setEntityTypeId(100);

                    final EntityStatus userStatus = entityManager.find(EntityStatus.class, entityStatusPK);

                    user.setEntityStatus(userStatus);

                    entityManager.persist(user);

                    //==========================================================
                    //  добавляем роли
                    // создание тестовой роли
                    final ApplicationRoleImpl role1 = NullSafe.createObject(ApplicationRoleImpl.class);
                    role1.setRoleCode(testValue);
                    role1.setRoleName(testValue);
                    role1.setCreation_date(LocalDateTime.now());

                    final EntityStatusPK roleStatusPK = NullSafe.createObject(EntityStatusPK.class);

                    roleStatusPK.setEntityStatusId(1);
                    roleStatusPK.setEntityTypeId(101);

                    final EntityStatus roleStatus = entityManager.find(EntityStatus.class, roleStatusPK);

                    role1.setEntityStatus(roleStatus);

                    entityManager.persist(role1);

                });
        //==========================================================
        LogService.LogInfo(this.getClass(), () -> String.format("\\ %s: %d ================================================",
                "applicationUserRepository", applicationUserRepository.count()));
        //==========================================================
        LogService.LogInfo(this.getClass(), () -> String.format("\\ %s: %d ================================================",
                "applicationRoleRepository", applicationRoleRepository.count()));

    }

//    @Test
//    public void testXX() {
//        //this.initializeTest();
//        LogService.LogInfo(this.getClass(), () -> String.format("Unit test '%s' is running ",
//                this.getClass().getCanonicalName()));
//    }
}
