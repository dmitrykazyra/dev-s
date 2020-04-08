/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.boot;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.spring.unit.Unit4Test;
import org.junit.Test;
import test.config.SpringSecurityTestConfig;

/**
 *
 * @author N76VB
 */
public final class SpringSecurityTests2 extends Unit4Test<SpringSecurityBoot, SpringSecurityTestConfig> {

    @Test
    public void test2() {
        //this.initializeTest();
        LogService.LogInfo(this.getClass(), () -> String.format("Unit test '%s' is running ",
                this.getClass().getCanonicalName()));
    }

    @Test
    public void test3() {
        //this.initializeTest();
        LogService.LogInfo(this.getClass(), () -> String.format("Unit test '%s' is running ",
                this.getClass().getCanonicalName()));
    }
}
