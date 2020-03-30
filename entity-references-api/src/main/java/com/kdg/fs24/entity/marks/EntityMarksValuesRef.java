/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.marks;

import com.kdg.fs24.exception.references.ReferenceNotFound;
import com.kdg.fs24.references.core.AbstractReference;

/**
 *
 * @author kazyra_d
 */
public class EntityMarksValuesRef<T extends MarkValue> extends AbstractReference<MarkValue> {

    //==========================================================================
    public T getEntityMarkValueById(final Integer entity_mark_value_id) throws ReferenceNotFound {

        return (T) this.<T>findReference(() -> (this.findEntityMarkValueById(entity_mark_value_id)),
                String.format("Неизвестное значение марки (EntityMarksValuesRef.entity_mark_value_id=%d)", entity_mark_value_id));
    }

    //==========================================================================
    private T findEntityMarkValueById(final Integer entity_mark_value_id) {

        return (T) this.findCachedRecords((object) -> ((T) object).getEntity_mark_value_id().equals(entity_mark_value_id));
    }

}
