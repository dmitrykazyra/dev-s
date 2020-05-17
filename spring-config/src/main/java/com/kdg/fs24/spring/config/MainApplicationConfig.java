/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.config;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.spring.core.application.setup.ExceptionsCollectorBean;
import com.kdg.fs24.spring.core.mail.MailManager;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import com.kdg.fs24.spring.core.repository.JpaRepositoriesCoolection;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author N76VB
 */
public abstract class MainApplicationConfig extends AbstractApplicationConfiguration {

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
    @Scope(value = "singleton")
    public MailManager mailManager() {
        return NullSafe.<MailManager>createObject(MailManager.class);
    }

    @Bean
    public JpaRepositoriesCoolection repoManager() {
        return NullSafe.createObject(JpaRepositoriesCoolection.class);
    }
}
