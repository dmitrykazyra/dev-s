/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.test.core;

import java.time.LocalDateTime;
import com.kdg.fs24.application.core.locale.NLS;

/**
 *
 * @author kazyra_d
 */
public abstract class Utils4test {

    public static final String generateTestName(final Class clazz) {
        return String
                .format("test_%s_%s", clazz.getCanonicalName(), NLS.getStringDateTime(LocalDateTime.now()))
                .replace(" ", "_");
    }

}
