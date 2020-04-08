/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.kdg.fs24.spring.config.MainApplicationConfig;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.kdg.fs24.spring.security")
@PropertySource("classpath:application.properties")
/**
 *
 * @author N76VB
 */
public class SpringSecurityTestConfig extends MainApplicationConfig {
    
    public SpringSecurityTestConfig() {
        
    }
    
}
