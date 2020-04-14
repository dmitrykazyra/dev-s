/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.config;

/**
 *
 * @author N76VB
 */
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import com.kdg.fs24.config.*;
import com.kdg.fs24.spring.config.MainApplicationConfig;

@Configuration
//@ComponentScan(basePackages = "com.kdg.fs24.service")
//@EntityScan(basePackages = {"com.kdg.fs24.spring.security", "com.kdg.fs24.entity"})
@PropertySource("classpath:application.properties")
//@EnableJpaRepositories(basePackages = "com.kdg.fs24.repository")
@Data
@Import({SecurityConfig.class, EntityReferencesConfig.class})
//@Import({EntityReferencesConfig.class})
//Profile("development")
public class SecurityTestConfig extends MainApplicationConfig {

}
