/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.kdg.fs24.spring.config.MainApplicationConfig;
import org.springframework.context.annotation.ComponentScan;


/**
 *
 * @author N76VB
 */
@Configuration
@ComponentScan(basePackages = "com.kdg.fs24.wmonitor.boot")
//@ComponentScan(basePackages = "com.kdg.fs24.wmonitor.repository")
//@PropertySource("classpath:application.properties")
public class WebMonitorConfig extends MainApplicationConfig {

    public WebMonitorConfig() {
        super();
    }
    //@Autowired
    //private volatile PersistanceEntityManager persistanceEntityManager;
    //==========================================================================
//    public PersistanceEntityManager getPersistanceEntityManager() {
//        return persistanceEntityManager;
//    }
}
