/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.classes;

import com.kdg.fs24.entity.core.AbstractAction;
import com.kdg.fs24.entity.core.api.ActionCodeId;
import com.kdg.fs24.entity.core.api.ViewAction;
import com.kdg.fs24.entity.core.api.ActionClassesCollection;
import com.kdg.fs24.entity.core.api.LiasContractAction;
import com.kdg.fs24.entity.core.api.EntityContractConst;
import com.kdg.fs24.entity.reference.EntityReferencesService;
//import com.kdg.fs24.exception.api.ActClassesNotSpecifiedInCollection;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
//import com.kdg.fs24.services.api.ServiceLocator;
//import com.kdg.fs24.services.FS24JdbcService;
import com.kdg.fs24.entity.core.api.EntityConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Random;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;
import com.kdg.fs24.application.core.sysconst.SysConst;

/**
 *
 * @author N76VB
 */
public abstract class AbstractActionClassesCollection<T extends AbstractAction> extends AbstractClassesCollection
        implements ActionClassesCollection<T> {

    public AbstractActionClassesCollection() {
        super();
    }

    //==========================================================================
    @Override
    public void RegisterActionClass(final Class<T> actClass) {
//        LogService.LogInfo(this.getClass(), LogService.getCurrentObjProcName(this),
//                String.format("Register action {%s}",
//                        actClass.getCanonicalName()));
        this.RegisterAbstractClass(actClass);
    }

    //==========================================================================
    @Override
    public final Class<T> findActionClass(final int action_code_id) {

        return ServiceFuncs.<Class<T>>getCollectionElement(this.getObjectList(),
                p -> this.getAction_code(p).equals(action_code_id),
                String.format("%s exception: Unknown action code (%d)",
                        LogService.getCurrentObjProcName(this),
                        action_code_id));
    }

    // находим номер действия из аннотации
    public static final <T extends AbstractAction> Integer getAction_code(final Class<T> actClass) {
        Integer objActionCodeId = 0;

        objActionCodeId = ((ActionCodeId) AnnotationFuncs.getAnnotation(actClass, ActionCodeId.class)).action_code();

        assert ((objActionCodeId > 0)) :
                String.format("%s: (%s) - %s", LogService.getCurrentObjProcName(AbstractActionClassesCollection.class),
                        actClass.getSimpleName(),
                        "action_code_id is null or annotation is not defined");

        return objActionCodeId;
    }

    //==========================================================================
    public static final String getAction_name(final Class actClass) {
        final Integer objActionCodeId = AbstractActionClassesCollection.getAction_code(actClass);

        return SysConst.UNKNOWN;
//        return String.format("%d - %s", objActionCodeId,
//                ServiceLocator.find(EntityReferencesService.class).getActionNameById(objActionCodeId));
    }

    //==========================================================================
    public static final String getAction_name(final Integer action_code) {
        return SysConst.UNKNOWN;
        //       return ServiceLocator.find(EntityReferencesService.class).getActionNameById(action_code);
    }

    //==========================================================================
    public final Collection<Class<T>> getActionClassesList(final Boolean viewActionsOnly) {

        return ServiceFuncs.<Class<T>>filterCollection_Silent(this.getObjectList(),
                actClass -> (!viewActionsOnly || ((AnnotationFuncs.isAnnotated(actClass, LiasContractAction.class, EntityContractConst.CHECK_PARENT_CLASSES))
                // выполняемые из меню
                || (AnnotationFuncs.isAnnotated(actClass, ViewAction.class)))));
    }

    //--------------------------------------------------------------------------
    protected final void registerPackageActionClasses(final String modulePackage) {

        ReflectionFuncs.createPkgClassesCollection(modulePackage, AbstractAction.class)
                .stream()
                .unordered()
                .filter(p -> AnnotationFuncs.isAnnotated(p, ActionCodeId.class))
                .collect(Collectors.toList())
                .forEach((clazz) -> {
                    this.RegisterActionClass((Class<T>) clazz);
                });

        this.registerReferences();
    }

    //==========================================================================
    @Override
    protected final void registerReferences() {
        NullSafe.runNewThread(() -> {

            NullSafe.create()
                    .execute(() -> {
                        // рандомный sleep 
                        Thread.sleep((new Random()).ints(this.getDelayMin(), this.getDelayMax()).findFirst().getAsInt());
                        AbstractActionClassesCollection.updateActionCodesRef(this.getClass(), this.getActionClassesList(EntityConst.VIEW_ACTIONS_ONLY));
                    });

        });
    }
    //==========================================================================
    //private static String actList;

    protected static <T extends AbstractAction> void updateActionCodesRef(final Class clazz, final Collection<Class<T>> classesList) {

//        synchronized (AbstractClassesCollection.class) {
//
//            NullSafe.create()
//                    .execute(() -> {
//                        LogService.LogInfo(clazz, LogService.getCurrentObjProcName(clazz), () -> "start.".concat(clazz.getSimpleName()));
//                        ServiceLocator
//                                .find(FS24JdbcService.class)
//                                .createCallBath("{call core_insertorupdate_actioncodesref(:AC, :AN, :APP, :IO)}")
//                                .execBatch(stmt -> {
//
//                                    //actList = "";
//                                    classesList
//                                            .stream()
//                                            .forEach((actClass) -> {
//
//                                                NullSafe.create(((ActionCodeId) AnnotationFuncs.getAnnotation(actClass, ActionCodeId.class)))
//                                                        .safeExecute((ns_acId) -> {
//                                                            stmt.setParamByName("AC", ((ActionCodeId) ns_acId).action_code());
//                                                            stmt.setParamByName("AN", ((ActionCodeId) ns_acId).action_name());
//                                                            stmt.setParamByName("APP", getModuleName(actClass.getProtectionDomain().getCodeSource().getLocation().getFile()));
//                                                            stmt.setParamByName("IO", ((ActionCodeId) ns_acId).is_closed());
//                                                            stmt.addBatch();
//                                                        });
//                                            });
//
//                                    //LogService.LogInfo(clazz, actList);
//                                })
//                                .commit();
//                    });
//            LogService.LogInfo(clazz, LogService.getCurrentObjProcName(clazz), () -> "finish.".concat(clazz.getSimpleName()));
//        }
    }
}
