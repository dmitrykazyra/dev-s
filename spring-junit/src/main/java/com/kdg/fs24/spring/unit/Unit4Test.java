/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.unit;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.spring.boot.api.AbstractSpringBootApplication;
import com.kdg.fs24.spring.core.api.ApplicationConfiguration;
import com.kdg.fs24.application.core.service.funcs.GenericFuncs;
import java.util.Optional;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author N76VB
 */
public abstract class Unit4Test<BOOT extends AbstractSpringBootApplication, CONF extends ApplicationConfiguration> {

//    static class SpringBootMap extends ConcurrentHashMap<Class<? extends SpringBoot4Test>, SpringBoot4Test> {
//    }
    final static private String[] EMPTY_ARGS = new String[0];
    final static private SpringBootClasses BOOT_CLASSES = NullSafe.createObject(SpringBootClasses.class);

    public Unit4Test() {

        final Class thisClass = this.getClass();

        LogService.LogInfo(this.getClass(), () -> String.format("Unit test '%s' is running (%d ) ",
                thisClass.getCanonicalName(),
                BOOT_CLASSES.size()));

        final Class<BOOT> sbClass = (Class<BOOT>) GenericFuncs.<BOOT>getTypeParameterClass(thisClass, 0);
        final Class<CONF> appConfigClass = (Class<CONF>) GenericFuncs.<CONF>getTypeParameterClass(thisClass, 1);

        if (!ServiceFuncs.<Class<? extends AbstractSpringBootApplication>>getCollectionElement(BOOT_CLASSES, bootClass -> bootClass.getClass().equals(sbClass))
                .isPresent()) {

            AbstractSpringBootApplication.runSpringBootApplication(EMPTY_ARGS, sbClass);
            // инициализация контейнера
            AbstractSpringBootApplication.initializeContext(appConfigClass);

            //BOOT_APPS.put(sbClass, this);
            BOOT_CLASSES.add(sbClass);
        }
    }
}

//interface SpringBootEntry extends Map.Entry<Class<? extends SpringBoot4Test>, SpringBoot4Test> {
//}
//class SpringBootMap extends ConcurrentHashMap<Class<? extends SpringBoot4Test>, SpringBoot4Test> {
//}
