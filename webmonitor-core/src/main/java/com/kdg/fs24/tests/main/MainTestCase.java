/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.tests.main;

import com.kdg.fs24.test.api.TestCaseId;
import com.kdg.fs24.wmonitor.api.WMonitorConst;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;

import com.kdg.fs24.test.core.AbstractTestCase;
import java.util.Collection;

/**
 *
 * @author kazyra_d
 */
@TestCaseId(case_id = WMonitorConst.TEST_MAIN)
public final class MainTestCase<E extends ActionEntity> extends AbstractTestCase<E> {

    @Override
    public E execute() {

        final Collection<Class> col = ReflectionFuncs.createPkgClassesCollection("java.util", Throwable.class);

//        final MainMonitorService mainMonitorService = ServiceLocator.find(MainMonitorService.class);
//        LogService.LogInfo(col.getClass(), () -> "qqq");

//        mainMonitorService.getBonuses();

        return null;
    }
}
