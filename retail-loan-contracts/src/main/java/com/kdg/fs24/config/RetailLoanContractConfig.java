/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.config;

import com.kdg.fs24.spring.config.AbstractApplicationConfiguration;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author N76VB
 */
@Configuration
@ComponentScan(basePackages = "com.kdg.fs24.service")
@EntityScan(basePackages = {"com.kdg.fs24.entity",
    "com.kdg.fs24.references"})
@PropertySource("classpath:application.properties")
//@EnableJpaRepositories(basePackages = "com.kdg.fs24.repository")
@Data
public class RetailLoanContractConfig extends AbstractApplicationConfiguration {

//    @Bean
//    SecurityRepository securityRepository() {
//        return NullSafe.createObject(SecurityRepository.class);
//    }
}
