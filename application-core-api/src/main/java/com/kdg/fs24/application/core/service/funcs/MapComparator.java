/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.application.core.service.funcs;

import java.util.Map;

/**
 *
 * @author N76VB
 */
@FunctionalInterface
public interface MapComparator<K, V> {

    Boolean filterMap(Map.Entry<K, V> mapEntry);

}
