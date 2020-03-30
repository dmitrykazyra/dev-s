/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.tests.main;

import com.kdg.fs24.test.api.TestCaseId;
import com.kdg.fs24.wmonitor.api.WMonitorConst;
import com.kdg.fs24.test.core.AbstractTestCase;
import com.kdg.fs24.entity.core.api.ActionEntity;

/**
 *
 * @author kazyra_d
 */
@TestCaseId(case_id = WMonitorConst.TEST_MAIN)
public class TestCase_Main<E extends ActionEntity> extends AbstractTestCase<E> {

    @Override
    public E execute() {
        return null;
    }

}
