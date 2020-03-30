/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.core.api;

import com.kdg.fs24.application.core.log.LogService;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * @author N76VB
 */
public interface ApplicationBean {

    @PostConstruct
    public default void afterConstruction() {
        LogService.LogInfo(this.getClass(), () -> String.format("Been has been created (%s)", this.getClass().getCanonicalName()));
    }
    
    @PreDestroy
    public default void beforeDestroy() {
        LogService.LogInfo(this.getClass(), () -> String.format("Been has been destroyed (%s)", this.getClass().getCanonicalName()));
    }    
}
