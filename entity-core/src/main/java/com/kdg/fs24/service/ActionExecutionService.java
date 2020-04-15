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
import com.kdg.fs24.entity.core.api.ActionClassesPackages;
import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.entity.core.AbstractAction;
import com.kdg.fs24.entity.core.api.ActionCodeId;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author N76VB
 */
@Data
//@Service
public abstract class ActionExecutionService<E extends AbstractActionEntity, A extends AbstractAction> implements ApplicationRepositoryService {

    private final Map<Class<E>, Class<A>> ENT_CLASSES
            = ServiceFuncs.<Class<E>, Class<A>>getOrCreateMap(ServiceFuncs.MAP_NULL);
    @Value("${debug}")
    private String debugMode; // = SysConst.STRING_FALSE;

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    // выполнение действия над сущностью
    public void executeAction(final AbstractActionEntity entity, final Integer actionId) {

    }

    //==========================================================================
    @PostConstruct
    public void postActionExecutionService() {
        final Class thisClass = this.getClass();

        if (!AnnotationFuncs.isAnnotated(thisClass, EntityClassesPackages.class)) {
            throw new NoEntityClassesPackagesDefined(String.format("No EntityClassesPackages annotations defined for '%s' ",
                    thisClass.getCanonicalName()));
        }

        final String[] entityClassesPackages = AnnotationFuncs.<EntityClassesPackages>getAnnotation(thisClass, EntityClassesPackages.class).pkgList();

        // выясняем 
        Arrays.stream(entityClassesPackages)
                .forEach(entPkg -> {
                    ReflectionFuncs.createPkgClassesCollection(entPkg, AbstractActionEntity.class)
                            .stream()
                            .filter(p -> !p.isInterface())
                            .filter(p -> !Modifier.isAbstract(p.getModifiers()))
                            .filter(p -> AnnotationFuncs.isAnnotated(p, EntityTypeId.class))
                            .forEach((entClazz) -> {

                                // действия на сущности
                                if (!AnnotationFuncs.isAnnotated(entClazz, ActionClassesPackages.class)) {
                                    throw new NoActionClassesPackagesDefined(String.format("No ActionClassesPackagesDefined annotations defined for '%s' ",
                                            entClazz.getCanonicalName()));
                                }

                                final String[] actionsClassesPackages = AnnotationFuncs.<ActionClassesPackages>getAnnotation(entClazz, ActionClassesPackages.class).pkgList();
                                //this.RegisterEntityClass((Class< T>) clazz);
                                Arrays.stream(actionsClassesPackages)
                                        .forEach(actPkg -> {

                                            ReflectionFuncs.createPkgClassesCollection(actPkg, AbstractAction.class)
                                                    .stream()
                                                    .filter(p -> !p.isInterface())
                                                    .filter(p -> !Modifier.isAbstract(p.getModifiers()))
                                                    .filter(p -> AnnotationFuncs.isAnnotated(p, ActionCodeId.class))
                                                    .forEach((actClazz) -> {
                                                        this.registerEntClass((Class<E>) entClazz, (Class<A>) actClazz);
                                                    });
                                        });
                            });
                });

        if (this.ENT_CLASSES.isEmpty()) {
            throw new NoActionClassesDefined(String.format("Action classes is empty for '%s' ",
                    thisClass.getCanonicalName()));
        }

        // post
        LogService.LogInfo(thisClass, () -> String.format("There %d pair(s) (entities/action): '%s' ",
                this.ENT_CLASSES.size(),
                this.getClass().getCanonicalName()));
    }
    //==========================================================================

    private void registerEntClass(final Class<E> entClass, final Class<A> actClass) {
        if (this.debugMode.equals(SysConst.STRING_TRUE)) {
            LogService.LogInfo(this.getClass(), () -> String.format("Add entity class/action: %s->%s",
                    entClass.getCanonicalName(),
                    actClass.getCanonicalName()));
        }
        this.ENT_CLASSES.put(entClass, actClass);
    }
}
//==============================================================================

class NoEntityClassesPackagesDefined extends InternalAppException {

    public NoEntityClassesPackagesDefined(final String message) {
        super(message);
    }
}

class NoActionClassesPackagesDefined extends InternalAppException {

    public NoActionClassesPackagesDefined(final String message) {
        super(message);
    }
}

//==============================================================================
class NoActionClassesDefined extends InternalAppException {

    public NoActionClassesDefined(final String message) {
        super(message);
    }
}
