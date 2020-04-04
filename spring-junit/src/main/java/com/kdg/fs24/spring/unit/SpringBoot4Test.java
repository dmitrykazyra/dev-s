/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.unit;

import com.kdg.fs24.application.core.service.funcs.GenericFuncs;
import com.kdg.fs24.spring.boot.api.AbstractSpringBootApplication;
import com.kdg.fs24.spring.core.api.ApplicationConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author N76VB
 */
@SpringBootApplication
@ComponentScan
public abstract class SpringBoot4Test<T extends ApplicationConfiguration> extends AbstractSpringBootApplication {
        
    final static private String[] EMPTY_ARGS = new String[0];
    
    public final void doTest() {
        
        final Class<T> appConfigClass = (Class<T>) GenericFuncs.<T>getTypeParameterClass(this.getClass());
        
        AbstractSpringBootApplication.runSpringBootApplication(EMPTY_ARGS, appConfigClass);
        // инициализация контейнера
        AbstractSpringBootApplication.initializeContext(appConfigClass);
    }
}
