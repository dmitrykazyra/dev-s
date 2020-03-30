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
import com.kdg.fs24.spring.core.bean.AbstractApplicationConfiguration;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import com.kdg.fs24.spring.core.application.setup.ExceptionsCollectorBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.ComponentScan;
import com.kdg.fs24.spring.core.mail.MailManager;

/**
 *
 * @author N76VB
 */
@Configuration
@ComponentScan(basePackages = "com.kdg.fs24.wmonitor.boot")
//@ComponentScan(basePackages = "com.kdg.fs24.wmonitor.repository")
//@PropertySource("classpath:application.properties")
public class WebMonitorConfig extends AbstractApplicationConfiguration {

    public WebMonitorConfig() {
        super();
    }

    //@Autowired
    //private volatile PersistanceEntityManager persistanceEntityManager;
    @Bean
    @Scope(value = "singleton")
    public PersistanceEntityManager entityManager() {
        //return NullSafe.createObject(PersistanceEntityManager.class, "wmonitor");

        return NullSafe.createObject(PersistanceEntityManager.class);

    }

    @Bean
    public ExceptionsCollectorBean exceptionManager() {
        return NullSafe.createObject(ExceptionsCollectorBean.class);
    }

    @Bean
    public MailManager mailManager() {
        return NullSafe.createObject(MailManager.class);
    }
    //==========================================================================

//    public PersistanceEntityManager getPersistanceEntityManager() {
//        return persistanceEntityManager;
//    }
}
