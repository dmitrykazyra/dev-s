/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.application.core.nullsafe;

/**
 *
 * @author N76VB
 */
@FunctionalInterface
public interface CodeBlockEx2Result {

    Object someCode(Throwable th) throws Throwable;
}
