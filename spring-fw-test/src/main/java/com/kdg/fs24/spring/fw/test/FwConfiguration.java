/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.fw.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.kdg.fs24.spring.core.bean.AbstractApplicationConfiguration;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import com.kdg.fs24.spring.core.application.setup.ExceptionsCollectorBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import org.springframework.context.annotation.Lazy;

/**
 * @author N76VB
 */
@Configuration
//@Lazy
//@ComponentScan(basePackages = ["com.kdg.fs24.spring.fw.test"])
//@PropertySource("classpath:dЬ/jdbc2.properties")
public class FwConfiguration extends AbstractApplicationConfiguration {

    //@Autowired
    private final PersistanceEntityManager persistanceEntityManager = NullSafe.createObject(PersistanceEntityManager.class, "wmonitor");

    @Bean
   // @Scope(value="prototype")
    public PersistanceEntityManager entityManager() {
        //return NullSafe.createObject(PersistanceEntityManager.class, "wmonitor");
        return persistanceEntityManager;
    }

    @Bean
    public ExceptionsCollectorBean exceptionManager() {
        return NullSafe.createObject(ExceptionsCollectorBean.class);
    }
    //==========================================================================

//    public PersistanceEntityManager getPersistanceEntityManager() {
//        return persistanceEntityManager;
//    }
}
