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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.kdg.fs24.wmonitor.repository.ItemsRepository;
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
@EntityScan(basePackages = "com.kdg.fs24.wmonitor.entity")
public class WebMonitorBoot extends AbstractSpringBootApplication {

    public static void main(final String[] args) {
        // запуск приложения
        AbstractSpringBootApplication.runSpringBootApplication(args, WebMonitorBoot.class);
        // инициализация контейнера
        AbstractSpringBootApplication.initializeContext(WebMonitorConfig.class);

        // первичный запуск
//       final WebMonitorBean webMonitorBean =  getApplicationContext().getBean(WebMonitorBean.class);
//       
//       webMonitorBean.getBonuses();
//        final PersistanceEntityManager persistanceEntityManager = getApplicationContext().getBean("entityManager", PersistanceEntityManager.class);
//       
        //       persistanceEntityManager.getEmfProperties();
    }

    @Bean
    public CommandLineRunner demo(final ItemsRepository entityRepository) {
        return (args) -> {
            // save a few customers

            final String kw = "аист";

            final Optional<HibernateItem> op = entityRepository.findById(50878);
            final Collection<HibernateItem> op1 = entityRepository.findByKeyWord(kw);

            if (op1.size() > 0) {

                LogService.LogInfo(this.getClass(), () -> String.format("%s: %d ",
                        kw,
                        op1.size()));

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

}
