/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import com.kdg.fs24.repository.*;
import com.kdg.fs24.spring.config.AbstractApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import com.kdg.fs24.application.core.nullsafe.NullSafe;

/**
 *
 * @author N76VB
 */
@Configuration
//@ComponentScan(basePackages = "com.kdg.fs24.service")
//@EntityScan(basePackages = {"com.kdg.fs24.spring.security", "com.kdg.fs24.entity"})
@PropertySource("classpath:application.properties")
//@EnableJpaRepositories(basePackages = "com.kdg.fs24.repository")
@Data
public class SecurityConfig extends AbstractApplicationConfiguration {

    @Bean
    ApplicationUserRepository applicationUserRepository() {
        return NullSafe.createObject(ApplicationUserRepository.class);
    }

    @Bean
    ApplicationRoleRepository applicationRoleRepository() {
        return NullSafe.createObject(ApplicationRoleRepository.class);
    }
}
