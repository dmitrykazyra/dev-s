/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.api;

import java.lang.reflect.Field;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import java.io.Serializable;

/**
 *
 * @author kazyra_d
 */
public abstract class AbstractRefRecord implements Serializable {

    public final long calcRecordHash() {

        return NullSafe.create()
                .execute2result(() -> {
                    long recHash = 0;
                    Class clazz = AbstractRefRecord.this.getClass();
                    while (!clazz.equals(Object.class)) {
                        for (Field field : clazz.getDeclaredFields()) {
                            field.setAccessible(true);
                            if (NullSafe.notNull(field.get(AbstractRefRecord.this))) {
                                recHash += field.get(AbstractRefRecord.this).hashCode();
                            }
                        }
                        clazz = clazz.getSuperclass();
                    }
                    return recHash;
                }).<Long>getObject();
    }
}
