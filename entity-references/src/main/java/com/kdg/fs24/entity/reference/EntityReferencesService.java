/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.reference;

import com.kdg.fs24.entity.action.ActionCode;
import com.kdg.fs24.entity.action.ActionCodesRef;
import com.kdg.fs24.entity.kind.EntityKind;
import com.kdg.fs24.entity.kind.EntityKindsRef;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.entity.status.EntityStatusesRef;
import com.kdg.fs24.entity.type.EntityType;
import com.kdg.fs24.entity.type.EntityTypesRef;
import com.kdg.fs24.references.core.AbstractReferencesList;
//import com.kdg.fs24.services.api.Service;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.entity.attr.EntAttr;
import com.kdg.fs24.entity.attr.EntAttrsRef;

/**
 *
 * @author N76VB
 */
@Deprecated
public final class EntityReferencesService extends AbstractReferencesList {
 //       implements Service {

//    @Override
//    public void initializeService() {
//        this.registerPackageReferences("com.kdg.fs24.entity.attr");
//        Service.super.initializeService();
//
//    }

    public EntityReferencesService() {
        super();
        //this.registerPackageReferences("com.kdg.fs24.entity");
    }

    // тип сущности (справочник типов сущностей - EntityTypeRef)
    public <T extends EntityType, R extends EntityTypesRef> T getEntityTypeById(final Integer type_id) {

        return (T) (NullSafe.create()
                .execute2result(() -> {
                    return ((R) this.<R>findOrCreateReference(EntityTypesRef.class)).getEntityTypeById(type_id);
                })).<T>getObject();
    }

    // вид сущности (справочник типов сущностей - EntityKindsRef)
    public <T extends EntityKind, R extends EntityKindsRef> T getEntityKindById(final Integer type_id) {

        return (T) (NullSafe.create()
                .execute2result(() -> {
                    return ((R) this.<R>findOrCreateReference(EntityKindsRef.class)).getEntityKindById(type_id);
                })).<T>getObject();
    }

    // вид сущности (справочник типов сущностей - EntityKindsRef)
    public <R extends EntityKindsRef> String getEntityKindTitleById(final Integer kind_id) {

        return (NullSafe.create()
                .execute2result(() -> {
                    return ((R) this.<R>findOrCreateReference(EntityKindsRef.class)).getEntityKindTitleById(kind_id);
                })).<String>getObject();
    }

    // код действия (справочник кодов действий - ActionCodesRef)
    //==========================================================================
    public <T extends ActionCode, R extends ActionCodesRef> T getActionCodeById(final Integer action_code) {

        return (T) (NullSafe.create()
                .execute2result(() -> {
                    return ((R) this.<R>findOrCreateReference(ActionCodesRef.class)).getActionCodeById(action_code);
                })).<T>getObject();
    }

    // код действия (справочник кодов действий - ActionCodesRef)
    public <T extends ActionCode> String getActionNameById(final Integer action_code) {
        return ((T) this.getActionCodeById(action_code)).getActionName();
    }

    //==========================================================================
    // статуса сущности (справочник кодов действий - EntityStatusesRef)
    public <T extends EntityStatus, R extends EntityStatusesRef> T getEntityStatusById(final Integer entity_type_id, final Integer entity_status_id) {

        return (T) (NullSafe.create()
                .execute2result(() -> {
                    return ((R) this.<R>findOrCreateReference(EntityStatusesRef.class)).getEntityStatusById(entity_type_id, entity_status_id);
                })).<T>getObject();
    }

    // наименование статуса сущности (справочник кодов действий - EntityStatusesRef)
    public <R extends EntityStatusesRef> String getEntityStatusNameById(final Integer entity_type_id, final Integer entity_status_id) {

        return (NullSafe.create()
                .execute2result(() -> {
                    return ((R) this.<R>findOrCreateReference(EntityStatusesRef.class)).getEntityStatusNameById(entity_type_id, entity_status_id);
                })).<String>getObject();
    }

    //==========================================================================
    // атрибут сущности (справочник код атрибутов - EntAttrRef)
    public <T extends EntAttr, R extends EntAttrsRef> T getEntAttrById(final Integer attr_id) {

        return (NullSafe.create()
                .execute2result(() -> ((R) this.<R>findOrCreateReference(EntAttrsRef.class)).getEntityAttrById(attr_id)))
                .<T>getObject();
    }

    // наименование атрибут сущности (справочник код атрибутов - EntAttrRef)
    public <R extends EntAttrsRef> String getEntAttrNameById(final Integer attr_id) {

        return (NullSafe.create()
                .execute2result(() -> ((R) this.<R>findOrCreateReference(EntAttrsRef.class)).getEntityAttrById(attr_id).getAttr_name()))
                .<String>getObject();
    }
    //==========================================================================    
}
