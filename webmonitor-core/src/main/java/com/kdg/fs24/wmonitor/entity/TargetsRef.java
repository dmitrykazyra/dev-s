/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.entity;

import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
//import com.kdg.fs24.entity.api.EntityReferenceConst;
import com.kdg.fs24.references.core.AbstractReference;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.wmonitor.api.*;
import java.util.Collection;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;

/**
 *
 * @author kazyra_d
 */
public class TargetsRef extends AbstractReference<AbstractSpyEntity> {

    //--------------------------------------------------------------------------
    public static <T extends AbstractSpyEntity> void registerReference() {
        // сохраняем в бд записи справочника видов обязательств

//        AbstractReference.<T>store(() -> {
//
//            return NullSafe.create(WMonitorConst.SPY_ENTITY_CLASS.getCanonicalName())
//                    .execute2result(() -> {
//
//                        final Collection<T> refCollection = ServiceFuncs.<T>createCollection();
//
//                        ReflectionFuncs.createPkgClassesCollection(WMonitorConst.ENTITY_SPY_CLASSES_PACKAGE, WMonitorConst.SPY_ENTITY_CLASS)
//                                .stream()
//                                .unordered()
//                                .filter(p -> AnnotationFuncs.isAnnotated(p, WMonitorConst.TARGET_ANNOTATION_CLASS))
//                                .forEach((c_clazz) -> {
//                                    
//                                    final Class<AbstractSpyEntity> refClass = c_clazz;
//
//                                    final Target_id target_id = AnnotationFuncs.<Target_id>getAnnotation(c_clazz, WMonitorConst.TARGET_ANNOTATION_CLASS);
//                                    final Alg_id alg_id = AnnotationFuncs.<Alg_id>getAnnotation(c_clazz, Alg_id.class);
//                                    final Target_code target_code = AnnotationFuncs.<Target_code>getAnnotation(c_clazz, Target_code.class);
//                                    final Target_url target_url = AnnotationFuncs.<Target_url>getAnnotation(c_clazz, Target_url.class);
//                                    final Refresh_period refresh_period = AnnotationFuncs.<Refresh_period>getAnnotation(c_clazz, Refresh_period.class);
//                                    final Email email = AnnotationFuncs.<Email>getAnnotation(c_clazz, Email.class);
//                                    final Page_add page_add = AnnotationFuncs.<Page_add>getAnnotation(c_clazz, Page_add.class);
//
//                                    NullSafe.create()
//                                            .execute(() -> {
//
//                                                refCollection.add((T) NullSafe.createObject(refClass)
//                                                        .setId(target_id.id())
//                                                        .setAlg_id(alg_id.id())
//                                                        .setIs_actual(Boolean.FALSE)
//                                                        .setTarget_code(target_code.code())
//                                                        .setTarget_url(target_url.url())
//                                                        .setRefresh_period(refresh_period.period())
//                                                        .setEmail(email.mailto())
//                                                        .setPage_add(page_add.page_size())
//                                                );
//                                            });
//                                });
//
//                        return refCollection;
//                    }).<Collection<T>>getObject();
//            // id, target_code, is_actual, alg_id, target_url, refresh_period, email, page_add
//        }, "{call core_insertorupdate_TargetsRef(:ID, :TC, :ACT, :ALG, :TGT, :RFP, :EM, :ADD)}",
//                (stmt, record) -> {
//                    stmt.setParamByName("ID", record.getId())
//                            .setParamByName("TC", record.getTarget_code())
//                            .setParamByName("ACT", record.getIs_actual())
//                            .setParamByName("ALG", record.getAlg_id())
//                            .setParamByName("TGT", record.getTarget_url())
//                            .setParamByName("RFP", record.getRefresh_period())
//                            .setParamByName("EM", record.getEmail())
//                            .setParamByName("ADD", record.getPage_add());
//
//                });
    }
}
