/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.tce.api;

/**
 *
 * @author N76VB
 */
public interface ObjectBuilder<T> {

    void processNewObject(T t);
}
