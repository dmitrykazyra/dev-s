package repo;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.entity.type.EntityType;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import java.time.LocalDateTime;
import org.hibernate.Session;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import org.springframework.context.annotation.Import;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import com.kdg.fs24.spring.unit.Unit4Test;
import com.kdg.fs24.repository.*;
import com.kdg.fs24.repository.EntityStatusesRepository;
import lombok.Data;
import com.kdg.fs24.application.core.sysconst.SysConst;
import config.TestRepoConfig;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author N76VB
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestRepoConfig.class)
//@DataJpaTest
@Data
public class TestRepositories {

//public class TestRepositories extends Unit4Test<TestSpringBoot, TestRepoConfig> {
    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    @Autowired
    private EntityTypesRepository entityTypesRepository;

    @Autowired
    private EntityStatusesRepository entityStatusesRepository;
//
//    @Autowired(required = false)
//    private EntityStatusesRepository entityStatusesRepository;

    @Test
    public void test1() {
        //this.initializeTest();
        LogService.LogInfo(this.getClass(), () -> String.format("1. Unit test '%s' is running ",
                this.getClass().getCanonicalName()));
        persistanceEntityManager
                .executeTransaction((entityManager) -> {
                    this.printAllRepositories();
                    final EntityType entityType = NullSafe.createObject(EntityType.class);

                    final String testUserName = UUID.randomUUID().toString();

                    entityType.setEntityTypeId(SysConst.INTEGER_ZERO);
                    entityType.setEntityAppName(testUserName);
                    entityType.setEntityTypeName(testUserName);

                    entityManager.merge(entityType);

                });
    }

    @Test
    public void test2() {
        //==========================================================

        LogService.LogInfo(this.getClass(), () -> String.format("2. Unit test '%s' is running ",
                this.getClass().getCanonicalName()));
        persistanceEntityManager
                .executeTransaction((entityManager) -> {
                    this.printAllRepositories();
                    final EntityType entityType = entityManager.find(EntityType.class, 0);

                    entityManager.remove(entityType);

                });
    }

    @Test
    public void test3() {
        //==========================================================

        LogService.LogInfo(this.getClass(), () -> String.format("3. Unit test '%s' is running ",
                this.getClass().getCanonicalName()));
        this.printAllRepositories();
    }
//    @Test
//    public void testXX() {
//        //this.initializeTest();
//        LogService.LogInfo(this.getClass(), () -> String.format("Unit test '%s' is running ",
//                this.getClass().getCanonicalName()));
//    }

    private void printAllRepositories() {
        LogService.LogInfo(this.getClass(), () -> String.format("\\ %s: %d ================================================",
                "entityTypesRepository", entityTypesRepository.count()));
        entityTypesRepository
                .findAll()
                .forEach(es -> {

                    LogService.LogInfo(this.getClass(), () -> String.format("EntityType: %s ",
                            es.toString()));
                });
        LogService.LogInfo(this.getClass(), () -> String.format("\\ %s: %d ================================================",
                "entityStatusesRepositoryy", entityStatusesRepository.count()));
        entityStatusesRepository
                .findAll()
                .forEach(es -> {

                    LogService.LogInfo(this.getClass(), () -> String.format("EntityStatuses: %s ",
                            es.toString()));
                });
    }
}