/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.core;

import com.kdg.fs24.application.core.log.LogService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.entity.core.api.ActionRecord;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.entity.core.api.BusinessRule;
import com.kdg.fs24.entity.core.api.EntityWarningsList;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.kdg.fs24.abs.controller.AbstractController;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.core.api.Action;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.log.LogService;
import java.util.Collection;
import com.kdg.fs24.test.api.TestResult;
import java.util.Map;
import com.kdg.fs24.entity.core.api.Action4Test;
//import com.kdg.fs24.entity.core.attr.AttrsCollection;
import javax.annotation.PostConstruct;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.persistence.api.PersistenceEntity;
//import com.kdg.fs24.listeners.api.ListenersCollection;
//import com.kdg.fs24.entity.core.api.EntityEventListener;
//import com.kdg.fs24.listeners.api.Event;
//import com.kdg.fs24.persistence.api.PersistenceEntity;
//import com.kdg.fs24.tce.api.StopWatcher;

import lombok.Data;

// абстрактная сущность
//@MappedSuperclass
@Data
public abstract class AbstractActionEntity
        extends AbstractPersistenceEntity {
    
    private final Collection<Class<? extends AbstractAction>> actClasses = 
            ServiceFuncs.getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);    
    
    public void executeAction(final Integer actionId) {
        
    }

}
