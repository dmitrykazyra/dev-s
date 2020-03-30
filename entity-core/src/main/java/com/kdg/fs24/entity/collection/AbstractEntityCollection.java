/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.collection;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.classes.EntityClassesService;
import com.kdg.fs24.references.list.ObjectList;
import com.kdg.fs24.entity.core.api.ActionEntity;
//import com.kdg.fs24.entity.core.api.EntityCollection;
//import com.kdg.fs24.services.api.ServiceLocator;
import java.util.Collection;
import com.kdg.fs24.application.core.nullsafe.NullSafe;

/**
 *
 * @author kazyra_d
 */
// абстрактная коллекция классов Entity
public abstract class AbstractEntityCollection<T extends ActionEntity>
        extends ObjectList<T> {
        //implements EntityCollection<T> {

    public void RegisterEntityClass(final Class actClass) {
//        if (AbstractDbService.getLogServiceMsg()) {
//            LogService.writeLog(LogService.getCurrentObjProcName(this), String.format(" register entity class %s", entClass.getEntClass().getCanonicalName()));
//        }
        //ServiceLocator.find(EntityClassesService.class).RegisterEntityClass(actClass);
    }

    //==========================================================================
    public Collection<T> getEntitiesList() {
        return super.getObjectList();
    }

    //==========================================================================
//    public void addEntity(final T entity) {
//        if (entity.getJustCreated()) {
//            entity.resetJustCreatedFlag();
//        }
//        synchronized (this) {
//            this.getEntitiesList().add(entity);
//        }
//    }

    //==========================================================================
    public void removeEntity(final T entity) {
        synchronized (this) {
            this.getEntitiesList().remove(entity);
        }
    }

    //==========================================================================
    public T findEntity(final Long entity_id) {

        synchronized (this) {
            return ServiceFuncs.<T>getCollectionElement(this.getEntitiesList(),
                    p -> p.getEntity_id().equals(entity_id),
                    String.format("Entity not found(%d)", entity_id));
        }
    }

    //==========================================================================
    public T findEntity(final String s_entity_id) {

        return this.findEntity(NullSafe.<Long>getSafeNumeric(s_entity_id));
    }


    //==========================================================================
//    public T createEntityObject(final Long entity_id, final int entity_type_id) {
//
//        final T newEntity = (T) ServiceLocator
//                .find(EntityClassesService.class)
//                .createEntityObject(entity_id, entity_type_id);
//
//        if ((NullSafe.notNull(newEntity))
//                && (!newEntity.getJustCreated())) {
//            this.addEntity(newEntity);
//        }
//
//        return newEntity;
//
//    }

    //==========================================================================
//    public T createEntityObject(Long entity_id) throws EntityClassNotFound, EntityNotFound, CreateEntityException {
//
//        int entity_type_id = (int) ServiceLocator.find(FS24JdbcService.class)
//                .createQuery("SELECT e.entity_type_id\n"
//                        + "FROM entities e\n "
//                        + "WHERE e.entity_id=:E")
//                .setParamByName("E", entity_id)
//                .openAndProcessQuery((qry) -> {
//
//                    if (qry.next()) {
//                        qry.setQueryResult(qry.getInteger("entity_type_id"));
//                    }
//                }).getQueryResult();
//
//        if (!(entity_type_id > 0)) {
//            throw new EntityNotFound(String.format("entity not found (%d)", entity_id));
//        }
//        // создаем новую интитю
//        return this.createEntityObject(entity_id, entity_type_id);
//
//    }
    //==========================================================================
    public T updateEntityObject(final T entity) {

        T modified_entity = null;
//        synchronized (this) {
//            modified_entity = (T) this.findEntity(entity.getEntity_id());
//
//            if (NullSafe.notNull(modified_entity)) {
//                modified_entity.assignEntity(entity);
//            }
//        }

        return modified_entity;

    }

}
