/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.kdg.fs24.spring.config.MainApplicationConfig;
import com.kdg.fs24.service.RetailLoanContractActionsService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author N76VB
 */
@Configuration
@ComponentScan(basePackages = "com.kdg.fs24.service")
@EntityScan(basePackages = {"com.kdg.fs24.entity",
    "com.kdg.fs24.counterparties.api",
    "com.kdg.fs24.references.application",
    "com.kdg.fs24.entity.core.api",
    "com.kdg.fs24.tariff.core",
    "com.kdg.fs24.entity.contract",
    "com.kdg.fs24.entity.marks",    
    "com.kdg.fs24.spring.security",
    "com.kdg.fs24.loan.references.api",
    "com.kdg.fs24.bond.schedule.references.api"})
@PropertySource("classpath:application.properties")
//@EnableJpaRepositories(basePackages = "com.kdg.fs24.repository")
@Data
@Import({RetailLoanContractActionsService.class})
public class TestRLCConfig extends MainApplicationConfig {

}
