/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.application.core.service.funcs;

import java.util.ArrayList;
import java.util.Collection;
import org.reflections.Reflections;
/**
 *
 * @author N76VB
 */
public final class ReflectionFuncs {
       public static <T extends Class> Collection<T> createPkgClassesCollection(final String packageName, final T rootClazz) {

        return new ArrayList<>((Collection<T>) (new Reflections(packageName)).getSubTypesOf(rootClazz));
    }

    //==========================================================================
    public static <T> Collection<T> createPkgClassesCollectionExt(final String packageName, final Class<T> rootClazz) {

        return new ArrayList<>((Collection<T>) (new Reflections(packageName)).getSubTypesOf(rootClazz));
    } 
}
