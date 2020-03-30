/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.classes;

import static com.kdg.fs24.entity.classes.AbstractClassesCollection.getModuleName;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.entity.core.api.EntityKindId;
import com.kdg.fs24.entity.core.api.EntityKindsRef;
import com.kdg.fs24.entity.core.api.EntityStatusesRef;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import com.kdg.fs24.entity.marks.api.MarkId;
import com.kdg.fs24.entity.marks.api.MarkValueId;
import com.kdg.fs24.entity.marks.api.MarksRef;
import com.kdg.fs24.entity.marks.api.MarksValuesRef;
import com.kdg.fs24.entity.reference.EntityReferencesService;
import com.kdg.fs24.entity.status.EntityStatusId;
import com.kdg.fs24.application.core.log.LogService;
//import static com.kdg.fs24.references.list.ObjectList.getEJB;
import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
//import com.kdg.fs24.services.api.Service;
//import com.kdg.fs24.services.api.ServiceLocator;
//import com.kdg.fs24.services.FS24JdbcService;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.lang.reflect.Modifier;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;

/**
 *
 * @author N76VB
 */
public final class EntityClassesService<T extends ActionEntity> extends AbstractClassesCollection {
    //    implements Service {

    public EntityClassesService() {
        super();
    }

    //==========================================================================
    public void RegisterEntityClass(final Class<T> entClass) {
        this.getObjectList().add(entClass);
    }

    //==========================================================================
    public Class<T> findEntityClass(final int entity_type_id) {

        return ServiceFuncs.<Class<T>>getCollectionElement(this.getObjectList(),
                p -> this.getEntityTypeId(p).equals(entity_type_id),
                String.format("Unknown entity type (%d)", entity_type_id));

    }

    public T createEntityObject(final Long entity_id, final int entity_type_id) {

        return NullSafe.create()
                .execute2result(() -> NullSafe.createObject(this.findEntityClass(entity_type_id), entity_id)).<T>getObject();
    }

    //==========================================================================
    // найти номер сущности из аннотации
    public Integer getEntityTypeId(final Class<T> entClass) {
        return NullSafe.create()
                .execute2result(() -> {
                    return ((EntityTypeId) AnnotationFuncs.getAnnotation(entClass, EntityTypeId.class, SysConst.CHECK_PARENT_CLASS)).entity_type_id();
                })
                .catchException(e -> {
                    LogService.LogErr(entClass, () -> String.format("%s: (%s) - %s", LogService.getCurrentObjProcName(this),
                            entClass.getSimpleName(),
                            "entity_type_id is null or annotation is not defined"));
                })
                .<Integer>getObject();

    }
    //==========================================================================

//    public String getEntityTypeName(final Class<T> entClass) {
//        Integer entityTypeId = this.getEntityTypeId(entClass);
//
//        return String.format("%d - %s", entityTypeId, ServiceLocator.find(EntityReferencesService.class).getEntityTypeById(entityTypeId).getEntity_type_name());
//    }
    //==========================================================================

    public void registerPackageEntityClasses(final String modulePackage) {

        ReflectionFuncs.createPkgClassesCollection(modulePackage, ActionEntity.class)
                .stream()
                .filter(p -> !p.isInterface())
                .filter(p -> !Modifier.isAbstract(p.getModifiers()))
                .filter(p -> AnnotationFuncs.isAnnotated(p, EntityTypeId.class))
                .forEach((clazz) -> {
                    this.RegisterEntityClass((Class< T>) clazz);
                });
        //this.updateActionCodesRef();
        this.registerReferences();
    }

    //==========================================================================
    public void registerPackagesEntityClasses(final String[] modulePackages) {

        for (int i = 0; i < modulePackages.length; i++) {

            ReflectionFuncs.createPkgClassesCollection(modulePackages[i], ActionEntity.class)
                    .stream()
                    .filter(p -> !p.isInterface())
                    .filter(p -> !Modifier.isAbstract(p.getModifiers()))
                    .filter(p -> AnnotationFuncs.isAnnotated(p, EntityTypeId.class)
                    || AnnotationFuncs.isAnnotated(p, EntityKindId.class))
                    .forEach((clazz) -> {
                        this.RegisterEntityClass((Class< T>) clazz);
                    });
        }
        //this.updateActionCodesRef();
        this.registerReferences();
    }

    //--------------------------------------------------------------------------
    @Override
    protected void registerReferences() {
        NullSafe.runNewThread(() -> {

            NullSafe.create()
                    .execute(() -> {
                        // рандомный sleep 
                        Thread.sleep((new Random()).ints(this.getDelayMin(), this.getDelayMax()).findFirst().getAsInt());
                        EntityClassesService.updateEntityReferences(this.getClass(), this.getObjectList());
                    });

        });
    }

    //--------------------------------------------------------------------------
    protected static void updateEntityReferences(final Class clazz, final Collection<Class> classesList) {

//        synchronized (AbstractClassesCollection.class) {
//            NullSafe.create()
//                    .execute(() -> {
//                        LogService.LogInfo(clazz, LogService.getCurrentObjProcName(clazz), () -> "start.".concat(clazz.getSimpleName()));
//                        // сохраняем в бд запись о виде сущности
//                        (ServiceLocator
//                                .find(FS24JdbcService.class)
//                                .createCallBath("{call core_insertorupdate_entityTypesRef(:ETI, :ETN, :APP)}")
//                                .execBatch(stmt -> {
//
//                                    classesList
//                                            .stream()
//                                            .unordered()
//                                            .forEach((entClass) -> {
//
//                                                NullSafe.create((AnnotationFuncs.getAnnotation(entClass, EntityTypeId.class, SysConst.CHECK_PARENT_CLASS)))
//                                                        .safeExecute((ns_entTypeId) -> {
//                                                            stmt.setParamByName("ETI", ((EntityTypeId) ns_entTypeId).entity_type_id());
//                                                            stmt.setParamByName("ETN", ((EntityTypeId) ns_entTypeId).entity_type_name());
//                                                            stmt.setParamByName("APP", getModuleName(entClass.getProtectionDomain().getCodeSource().getLocation().getFile()));
//                                                            stmt.addBatch();
//                                                        });
//                                            });
//
//                                })).commit();
//                        // сохраняем в бд запись о типе сущности
//                        (ServiceLocator
//                                .find(FS24JdbcService.class)
//                                .createCallBath("{call core_insertorupdate_entityKindsRef(:ETI, :EKI, :EKN)}")
//                                .execBatch(stmt -> {
//
//                                    classesList
//                                            .stream()
//                                            .unordered()
//                                            .forEach((entClass) -> {
//
//                                                NullSafe.create(AnnotationFuncs.getAnnotation(entClass, EntityKindId.class))
//                                                        .safeExecute((ns_entityKindId) -> {
//                                                            stmt.setParamByName("ETI", ((EntityKindId) ns_entityKindId).entity_type_id());
//                                                            stmt.setParamByName("EKI", ((EntityKindId) ns_entityKindId).entity_kind_id());
//                                                            stmt.setParamByName("EKN", ((EntityKindId) ns_entityKindId).entity_kind_name());
//                                                            stmt.addBatch();
//                                                        });
//
//                                                NullSafe.create(AnnotationFuncs.getAnnotation(entClass, EntityKindsRef.class))
//                                                        .safeExecute((ns_entityKindsRef) -> {
//                                                            for (EntityKindId eki : ((EntityKindsRef) ns_entityKindsRef).kind_id()) {
//                                                                stmt.setParamByName("ETI", eki.entity_type_id());
//                                                                stmt.setParamByName("EKI", eki.entity_kind_id());
//                                                                stmt.setParamByName("EKN", eki.entity_kind_name());
//                                                                stmt.addBatch();
//                                                            }
//                                                        });
//                                            });
//
//                                })).commit();
//                        // сохраняем в бд записи статусах сущности
//                        (ServiceLocator
//                                .find(FS24JdbcService.class)
//                                .createCallBath("{call core_insertorupdate_entityStatusesRef(:ETI, :ESI, :ENS)}")
//                                .execBatch(stmt -> {
//
//                                    classesList
//                                            .stream()
//                                            .unordered()
//                                            .forEach((entClass) -> {
//
//                                                NullSafe.create(AnnotationFuncs.getAnnotation(entClass, EntityStatusesRef.class, SysConst.CHECK_PARENT_CLASS))
//                                                        .safeExecute((ns_entityStatusesRef) -> {
//
//                                                            for (EntityStatusId entiy_status : ((EntityStatusesRef) ns_entityStatusesRef).entiy_status()) {
//                                                                stmt.setParamByName("ETI", entiy_status.entity_type_id());
//                                                                stmt.setParamByName("ESI", entiy_status.entity_status_id());
//                                                                stmt.setParamByName("ENS", entiy_status.entity_status_name());
//                                                                stmt.addBatch();
//                                                            }
//                                                        });
//                                            });
//
//                                })).commit();
//                        // сохраняем в бд записи видах отметок
//                        (ServiceLocator
//                                .find(FS24JdbcService.class)
//                                .createCallBath("{call core_insertorupdate_marksRef(:MI, :MN, :MG)}")
//                                .execBatch(stmt -> {
//
//                                    classesList
//                                            .stream()
//                                            .forEach((entClass) -> {
//
//                                                NullSafe.create(AnnotationFuncs.getAnnotation(entClass, MarksRef.class, SysConst.CHECK_PARENT_CLASS))
//                                                        .safeExecute((ns_entityStatusesRef) -> {
//
//                                                            for (MarkId mi : ((MarksRef) ns_entityStatusesRef).mark()) {
//                                                                stmt.setParamByName("MI", mi.mark_id());
//                                                                stmt.setParamByName("MN", mi.mark_name());
//                                                                stmt.setParamByName("MG", mi.mark_group());
//                                                                stmt.addBatch();
//                                                            }
//                                                        });
//                                            });
//
//                                })).commit();
//                        // сохраняем в бд записи о значениях отметок
//                        (ServiceLocator
//                                .find(FS24JdbcService.class)
//                                .createCallBath("{call core_insertorupdate_mark_value_ref(:MI, :MVI, :MVN)}")
//                                .execBatch(stmt -> {
//
//                                    classesList
//                                            .stream()
//                                            .unordered()
//                                            .forEach((entClass) -> {
//
//                                                NullSafe.create(AnnotationFuncs.getAnnotation(entClass, MarksValuesRef.class, SysConst.CHECK_PARENT_CLASS))
//                                                        .safeExecute((ns_marksValuesRef) -> {
//
//                                                            for (MarkValueId mv : ((MarksValuesRef) ns_marksValuesRef).mark_value()) {
//                                                                stmt.setParamByName("MI", mv.mark_id());
//                                                                stmt.setParamByName("MVI", mv.mark_value_id());
//                                                                stmt.setParamByName("MVN", mv.mark_value_name());
//                                                                stmt.addBatch();
//                                                            }
//                                                        });
//                                            });
//
//                                })).commit();
//                    });
//            LogService.LogInfo(clazz, LogService.getCurrentObjProcName(clazz), () -> "finish.".concat(clazz.getSimpleName()));
//        }
    }
}
