/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.classes;

import com.kdg.fs24.references.list.ObjectList;
import com.kdg.fs24.entity.core.api.ClassesCollection;
import com.kdg.fs24.application.core.sysconst.SysConst;

/**
 *
 * @author N76VB
 */
public abstract class AbstractClassesCollection<T extends Object> extends ObjectList
        implements ClassesCollection {

    private final int delayMin = 3000;
    private final int delayMax = 5000;

    protected int getDelayMin() {
        return delayMin;
    }

    protected int getDelayMax() {
        return delayMax;
    }

    //==========================================================================
    public AbstractClassesCollection() {
        super();
    }

    public void RegisterAbstractClass(final Class<T> abstractClass) {
        this.getObjectList().add(abstractClass);
    }

    //==========================================================================
    protected abstract void registerReferences();
    //==========================================================================

    protected static String getModuleName(final String classUrl) {

        String moduleName = SysConst.EMPTY_STRING;
        String url = classUrl;

        while (moduleName.lastIndexOf(".") < 0) {

            if (!moduleName.isEmpty()) {
                url = url.substring(0, url.lastIndexOf("/WEB-INF"));
            }

            moduleName = url.substring(url.lastIndexOf("/") + 1);

        }

        return moduleName;
    }
}
