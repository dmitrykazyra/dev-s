/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.tariff.core;

import com.kdg.fs24.application.core.exception.api.InternalAppException;

/**
 *
 * @author N76VB
 */
public class DuplicateServIdException extends InternalAppException {

    public DuplicateServIdException(final String message) {
        super(message);
    }

}