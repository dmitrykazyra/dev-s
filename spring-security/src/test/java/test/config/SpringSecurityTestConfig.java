/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.kdg.fs24.spring.config.MainApplicationConfig;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//@ComponentScan(basePackages = "com.kdg.fs24.spring.security")
@EntityScan(basePackages = "com.kdg.fs24.entity.type")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "com.kdg.fs24.repository")
@Data
/**
 *
 * @author N76VB
 */
public class SpringSecurityTestConfig extends MainApplicationConfig {

}
