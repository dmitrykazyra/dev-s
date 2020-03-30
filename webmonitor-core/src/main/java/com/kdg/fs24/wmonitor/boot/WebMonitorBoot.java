/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.boot;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import com.kdg.fs24.spring.boot.api.AbstractSpringBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.kdg.fs24.wmonitor.config.WebMonitorConfig;
import com.kdg.fs24.wmonitor.entity.HibernateItem;
import com.kdg.fs24.wmonitor.query.KeyWordRecords;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.kdg.fs24.wmonitor.repository.ItemsRepository;
import com.kdg.fs24.wmonitor.repository.KeyWordsRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import java.util.Optional;
import java.util.Collection;

/**
 *
 * @author N76VB
 */
@SpringBootApplication
@ComponentScan
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "com.kdg.fs24.wmonitor.repository")
@EntityScan(basePackages = "com.kdg.fs24.wmonitor")
public class WebMonitorBoot extends AbstractSpringBootApplication {

    public static void main(final String[] args) {
        // запуск приложения
        AbstractSpringBootApplication.runSpringBootApplication(args, WebMonitorBoot.class);
        // инициализация контейнера
        AbstractSpringBootApplication.initializeContext(WebMonitorConfig.class);
    }

    @Bean
    public CommandLineRunner demo(final ItemsRepository entityRepository) {
        return (args) -> {
            // save a few customers

            final String kw = "980";

            final Optional<HibernateItem> op = entityRepository.findById(50878);
            final Collection<HibernateItem> op1 = entityRepository.findByKeyWord(kw.toLowerCase());

            LogService.LogInfo(this.getClass(), () -> String.format("%s: %d entries ",
                    kw,
                    op1.size()));

            if (op1.size() > 0) {

                op1.forEach(item -> LogService.LogInfo(this.getClass(), () -> String.format("findAll->item: %s (%s)'",
                        item.getItem_url(),
                        item.getLinkHeader()))
                );
            }

            LogService.LogInfo(this.getClass(), () -> String.format("entityRepository.count: %d ",
                    entityRepository.count()));

//            final Iterable<HibernateItem> i = entityRepository.findAll();
//
//            i.forEach(item -> {
//                LogService.LogInfo(this.getClass(), () -> String.format("findAll->item: %s (%s)'",
//                        item.getItem_url(),
//                        item.getLinkHeader()));
//
//            });
        };
    }

    //==========================================================================
    @Bean
    public CommandLineRunner demo2(final KeyWordsRepository keyWordsRepository) {
        return (args) -> {

            final Collection<KeyWordRecords> op2 = keyWordsRepository.findKeyWords();

            LogService.LogInfo(this.getClass(), () -> String.format("KeyWordRecords: %d entries ",
                    op2.size()));

        };
    }

}
