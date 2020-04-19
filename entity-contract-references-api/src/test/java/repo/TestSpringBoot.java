/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repo;

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
@ComponentScan(basePackages = "com.kdg.fs24.service")
@PropertySource("classpath:application.properties")
@EntityScan(basePackages = {"com.kdg.fs24.entity"})
@EnableJpaRepositories(basePackages = "com.kdg.fs24.repository")
public class TestSpringBoot extends SpringBoot4Test {
    
}
