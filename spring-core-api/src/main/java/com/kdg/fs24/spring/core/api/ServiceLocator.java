/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.core.api;

/**
 *
 * @author N76VB
 */
import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import lombok.Data;
import java.util.Collection;
import java.util.Optional;

@Data
public final class ServiceLocator {

    private static final Collection<ApplicationBean> BEANS_LIST
            = ServiceFuncs.<ApplicationBean>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

    public static void registerService(final ApplicationBean applicationBean) {
        BEANS_LIST.add(applicationBean);
    }

    public static void releaseService(final ApplicationBean applicationBean) {
        BEANS_LIST.remove(applicationBean);
    }

    public static <T> T findService(final Class<T> clazz) {
        final Optional<ApplicationBean> service = ServiceFuncs.<ApplicationBean>getCollectionElement(
                BEANS_LIST,
                srv -> srv.getClass().equals(clazz));

        if (!service.isPresent()) {
            class UnkonwnService extends InternalAppException {

                public UnkonwnService(final String message) {
                    super(message);
                }
            }
            throw new UnkonwnService(String.format("ServiceLocator: Unknown service '%s' ",
                    clazz.getCanonicalName()));
        }

        return (T) service.get();
    }

}
