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
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import lombok.Data;
import java.util.Collection;

@Data
public final class ServiceLocator {

    private static final Collection<ApplicationBean> BEANS_LIST
            = ServiceFuncs.<ApplicationBean>createCollection();

    public static void registerService(final ApplicationBean applicationBean) {
        BEANS_LIST.add(applicationBean);
    }

    public static void releaseService(final ApplicationBean applicationBean) {
        BEANS_LIST.remove(applicationBean);
    }

    public static <T> T findService(final Class<T> clazz) {
        return (T) ServiceFuncs.<ApplicationBean>findCollectionElement(
                BEANS_LIST,
                srv -> srv.getClass().equals(clazz) || clazz.isAssignableFrom(srv.getClass()),
                String.format("%s: Can't find bean/service '%s' ",
                        ServiceLocator.class.getSimpleName(),
                        clazz.getCanonicalName()));
    }

}
