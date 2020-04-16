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
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.entity.core.AbstractAction;
import com.kdg.fs24.entity.core.api.ActionCodeId;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import com.kdg.fs24.repository.ActionCodesRepository;
import com.kdg.fs24.entity.action.ActionCode;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import java.util.Collection;
import org.springframework.lang.NonNull;

/**
 *
 * @author N76VB
 */
@Data
//@Service
public abstract class ActionExecutionService<E extends AbstractActionEntity, A extends AbstractAction>
        implements ApplicationRepositoryService {

    private final Map<Class<E>, Class<A>> CLASS_ENT2ACTION
            = ServiceFuncs.<Class<E>, Class<A>>getOrCreateMap(ServiceFuncs.MAP_NULL);

    private final Map<Integer, Class<A>> CLASS_INT2ACTION
            = ServiceFuncs.<Integer, Class<A>>getOrCreateMap(ServiceFuncs.MAP_NULL);

    @Value("${debug}")
    private String debugMode; // = SysConst.STRING_FALSE;

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    @Autowired
    private ActionCodesRepository actionCodesRepository;

    //==========================================================================
    // выполнение действия над сущностью
    public void executeAction(final AbstractActionEntity entity, final Integer action_code) {
        final Optional<Class<A>> optActClass = ServiceFuncs.getMapValue(CLASS_INT2ACTION, mapEntry -> mapEntry.getKey().equals(action_code));

        if (!optActClass.isPresent()) {
            throw new UnknownActionCode(String.format("Unknown action_code (%d)", action_code));
        }

        final Class<A> actClass = optActClass.get();

        final A action = NullSafe.createObject(actClass);

        final Optional<ActionCode> ac = actionCodesRepository.findById(action_code);

        if (!ac.isPresent()) {
            throw new NoActionCodeDefined(String.format("Unknown actionCode (%d)", action_code));
        }

        action.setPersistanceEntityManager(persistanceEntityManager);
        action.setEntity(entity);
        action.setActionCode(ac.get());

        //action.execute(entity, ac.get());
        action.execute();
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
                                                        this.registerEntClass((Class<E>) entClazz,
                                                                (Class<A>) actClazz,
                                                                AnnotationFuncs.getAnnotation(actClazz, ActionCodeId.class).action_code());
                                                    });
                                        });
                            });
                });

        if (this.CLASS_ENT2ACTION.isEmpty()) {
            throw new NoActionClassesDefined(String.format("Action classes is empty for '%s' ",
                    thisClass.getCanonicalName()));
        }

        // post
        LogService.LogInfo(thisClass, () -> String.format("There %d pair(s) (entities/action): '%s' ",
                this.CLASS_ENT2ACTION.size(),
                this.getClass().getCanonicalName()));
    }
    //==========================================================================

    private void registerEntClass(final Class<E> entClass, final Class<A> actClass, final Integer action_code) {
        if (this.debugMode.equals(SysConst.STRING_TRUE)) {
            LogService.LogInfo(this.getClass(), () -> String.format("Add entity class/action: %s->%s",
                    entClass.getCanonicalName(),
                    actClass.getCanonicalName()));
        }
        this.CLASS_ENT2ACTION.put(entClass, actClass);
        this.CLASS_INT2ACTION.put(action_code, actClass);
    }
}
//==============================================================================

class NoActionCodeDefined extends InternalAppException {

    public NoActionCodeDefined(final String message) {
        super(message);
    }
}

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

//==============================================================================
class UnknownActionCode extends InternalAppException {

    public UnknownActionCode(final String message) {
        super(message);
    }
}
