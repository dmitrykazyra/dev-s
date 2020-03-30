/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.collection;

import com.kdg.fs24.entity.core.api.BusinessRule;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.entity.core.api.EntityClass;
import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.references.list.ObjectList;
import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
//import com.kdg.fs24.services.api.Service;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import java.util.List;

/**
 *
 * @author N76VB
 */
public class EntityBusinessRulesListService extends ObjectList { //implements Service {

    public <T extends BusinessRule> void registerBusinessRule(final Class<T> brClass) {

        // todo - региструем только нужное правило, заданное в реестре настроек
        this.getObjectList().add(brClass);
    }

    //==========================================================================
    // найти бизнесс-правило для сущности
    public <T extends ActionEntity> BusinessRule findBusinessRule(final Class<T> brClass) {

        BusinessRule businessRule = null;

        for (Class<BusinessRule> classBr : (List<Class<BusinessRule>>) this.getObjectList()) {

            if (this.getAppliedClass(classBr).equals(brClass)) {
                businessRule = (NullSafe.create()
                        .execute2result(() -> NullSafe.createObject(classBr)))
                        .<BusinessRule>getObject();
                break;
            }
        }

        return businessRule;
    }

    //==========================================================================
    // класс, применяемый к бизнесс-правилу
    private <T extends ActionEntity> Class<T> getAppliedClass(final Class<BusinessRule> brClass) {
        // достаем ID из аннтоации
        return (NullSafe.create()
                .execute2result(() -> {
                    return (Class<T>) ((EntityClass) AnnotationFuncs.getAnnotation(brClass, EntityClass.class)).entityClass();
                }))
                .catchException((e) -> {
                    assert (true) :
                            String.format("%s: (%s) - %s", LogService.getCurrentObjProcName(this),
                                    brClass.getSimpleName(),
                                    "entity_class is null or annotation is not defined");
                })
                .<Class<T>>getObject();

    }
}
