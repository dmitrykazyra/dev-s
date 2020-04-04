/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.kdg.fs24.spring.config.MainApplicationConfig;

@Configuration
@ComponentScan(basePackages = "com.kdg.fs24.spring.security")
/**
 *
 * @author N76VB
 */
public class SpringSecurityTestConfig extends MainApplicationConfig {
    
}
