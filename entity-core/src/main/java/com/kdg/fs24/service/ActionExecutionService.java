/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
import com.kdg.fs24.spring.core.api.ApplicationRepositoryService;
import lombok.Data;
import org.springframework.stereotype.Service;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import com.kdg.fs24.entity.core.api.ViewAction;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import com.kdg.fs24.entity.core.api.EntityClassesPackages;
import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.core.AbstractAction;
import java.util.Collection;

/**
 *
 * @author N76VB
 */
@Data
//@Service
public abstract class ActionExecutionService implements ApplicationRepositoryService {

    private final Collection<Class<? extends AbstractActionEntity>> entClasses = 
            ServiceFuncs.getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

    
    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    // выполнение дествия над сущностью
    public void executeAction(final AbstractActionEntity entity, final Integer actionId) {

    }

    //==============/============================================================
    @PostConstruct
    public void postActionExecutionService() {
        final Class thisClass = this.getClass();

        if (!AnnotationFuncs.isAnnotated(thisClass, EntityClassesPackages.class)) {
            throw new NoEntityClassesPackagesDefined(String.format("No EntityClassesPackages annotations defined for '%s' ",
                    thisClass.getCanonicalName()));
        }

        final String[] classesPackages = AnnotationFuncs.<EntityClassesPackages>getAnnotation(thisClass, EntityClassesPackages.class).pkgList();

        // post
        LogService.LogInfo(thisClass, () -> String.format("postActionExecutionService: '%s' ",
                this.getClass().getCanonicalName()));

    }

}

class NoEntityClassesPackagesDefined extends InternalAppException {

    public NoEntityClassesPackagesDefined(final String message) {
        super(message);
    }
}
