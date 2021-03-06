/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.boot.api;

/**
 *
 * @author N76VB
 */
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.kdg.fs24.spring.core.api.ApplicationConfiguration;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@SpringBootApplication
//@ComponentScan
public abstract class AbstractSpringBootApplication {

    private static volatile ApplicationContext applicationContext;

    public static void runSpringBootApplication(final String[] args,
            final Class springBootClass) {
        //SpringApplication.run(SpringBootApplication.class, args);
        SpringApplication.run(springBootClass, args);
    }
//    public static void main(final String[] args) {
//        //SpringApplication.run(SpringBootApplication.class, args);
//        SpringApplication.run(AbstractSpringBootApplication.class, args);
//    }

    public static void initializeContext(final Class<? extends ApplicationConfiguration> clazz) {
        // AbstractSpringBootApplication.ctx = NullSafe.createObject(AnnotationConfigApplicationContext.class, clazz);
        AbstractSpringBootApplication.applicationContext = new AnnotationConfigApplicationContext(clazz);
    }

    public static ApplicationContext getApplicationContext() {
        return AbstractSpringBootApplication.applicationContext;
    }
}
