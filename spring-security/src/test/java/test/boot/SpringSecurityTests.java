/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.boot;

import com.kdg.fs24.application.core.log.LogService;
import test.config.SpringSecurityTestConfig;
import com.kdg.fs24.spring.unit.Unit4Test;
import org.junit.Test;

/**
 *
 * @author N76VB
 */
//public class SpringSecurityTests extends SpringBoot4Test<SpringSecurityTestConfig> {
public final class SpringSecurityTests extends Unit4Test<SpringSecurityBoot, SpringSecurityTestConfig> {

    @Test
    public void test1() {
        //this.initializeTest();
        LogService.LogInfo(this.getClass(), () -> String.format("Unit test '%s' is running ",
                this.getClass().getCanonicalName()));
    }

}
