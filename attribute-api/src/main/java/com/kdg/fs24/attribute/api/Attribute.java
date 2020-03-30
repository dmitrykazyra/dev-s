/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.attribute.api;

/**
 *
 * @author N76VB
 */
@FunctionalInterface
public interface Attribute<T> {

    T value();
    
//    void assign(T t);
}
