/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.api;

import com.kdg.fs24.application.core.exception.api.InternalAppException;
import java.lang.reflect.Field;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.FilterComparator;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
public abstract class AbstractRefRecord implements PersistenceEntity {

    public static final Map<Class<? extends AbstractRefRecord>, Collection<? extends AbstractRefRecord>> REF_CACHE
            = ServiceFuncs.getOrCreateMap_Safe(ServiceFuncs.MAP_NULL);

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

    //==========================================================================
    public static <T extends AbstractRefRecord> T getRefeenceRecord(
            final Class<T> clazz,
            final FilterComparator<T> filterComparator) {
        final Optional<T> optional
                = ServiceFuncs.getMapValue(AbstractRefRecord.REF_CACHE, mapEntry -> mapEntry.getKey().equals(clazz))
                        .get()
                        .stream()
                        .map(x -> (T) x)
                        .collect(Collectors.toList())
                        .stream()
                        .filter((fltr) -> filterComparator.getFilter(fltr))
                        .findFirst();

        if (!optional.isPresent()) {

            class ActRefeenceRecordIsNotFound extends InternalAppException {

                public ActRefeenceRecordIsNotFound(final String message) {
                    super(message);
                }
            }
            throw new ActRefeenceRecordIsNotFound(String.format("%s is not found (%s) ",
                    clazz.getSimpleName(), filterComparator.toString()));
        }

        return optional.get();
    }
}
