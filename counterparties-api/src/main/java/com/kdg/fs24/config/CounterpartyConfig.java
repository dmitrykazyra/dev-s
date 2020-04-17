/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.config;

import com.kdg.fs24.spring.config.AbstractApplicationConfiguration;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author N76VB
 */
@Configuration
@ComponentScan(basePackages = "com.kdg.fs24.service")
@PropertySource("classpath:application.properties")
@Data
public class CounterpartyConfig extends AbstractApplicationConfiguration {
}
