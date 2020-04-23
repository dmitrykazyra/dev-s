/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.kdg.fs24.spring.unit.SpringBoot4Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author N76VB
 */
@SpringBootApplication
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "com.kdg.fs24.repository")
public class TestSpringBoot extends SpringBoot4Test {

}
