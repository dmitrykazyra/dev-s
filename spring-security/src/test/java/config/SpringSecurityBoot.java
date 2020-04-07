/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import com.kdg.fs24.spring.unit.SpringBoot4Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author N76VB
 */
@SpringBootApplication
@ComponentScan
@PropertySource("classpath:application.properties")
public class SpringSecurityBoot extends SpringBoot4Test {
    
}
