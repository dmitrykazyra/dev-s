/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.application.core.exception.contracts.api;

import com.kdg.fs24.application.core.exception.api.InternalAppException;

/**
 *
 * @author kazyra_d
 */
public class LiasOperNotFoundException extends InternalAppException {

    public LiasOperNotFoundException(final String message) {
        super(message);
    }

}
