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
public class EntityMarksRef<T extends Mark> extends AbstractReference<Mark> {

    //==========================================================================
    public T getEntityTById(final Integer entity_mark_id) throws ReferenceNotFound {

        return (T) this.<T>findReference(() -> (this.findEntityMarkById(entity_mark_id)),
                String.format("Неизвестный вид марки (EntityMarksRef.mark_id=%d)", entity_mark_id));
    }

    //==========================================================================
    private T findEntityMarkById(final Integer entity_mark_id) {

        return (T) this.findCachedRecords((object) -> ((T) object).getMark_id().equals(entity_mark_id));
    }
}
