/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.status;

import com.kdg.fs24.exception.references.ReferenceNotFound;
import com.kdg.fs24.references.core.AbstractReference;

/**
 *
 * @author kazyra_d
 */
public class EntityStatusesRef<T extends EntityStatus> extends AbstractReference<EntityStatus> {

    //==========================================================================
    public String getEntityStatusNameById(final Integer type_id, final Integer status_id) throws ReferenceNotFound {

        return this.getEntityStatusById(type_id, status_id).getEntity_status_name();
    }

    //==========================================================================
    public T getEntityStatusById(final Integer type_id, final Integer status_id) throws ReferenceNotFound {

        return (T) this.<T>findReference(() -> (this.findEntityStatusById(type_id, status_id)),
                String.format("Неизвестный тип сущности (EntityStatusesRef.app_id=%d)", type_id));

    }

    //==========================================================================
    private T findEntityStatusById(final Integer type_id, final Integer status_id) {

        return (T) this.findCachedRecords((
                (object) -> (((T) object).getEntity_type_id().equals(type_id)
                && ((T) object).getEntity_status_id().equals(status_id))));

    }
}
