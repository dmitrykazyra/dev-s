/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.api;

/**
 *
 * @author N76VB
 */
import java.util.Collection;

public interface ReferenceCollection<T extends ReferenceRec> {
    Collection<T> getRefCollection();
}