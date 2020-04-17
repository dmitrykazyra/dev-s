/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.persistence.core;

import com.kdg.fs24.persistence.api.PersistenceEntity;

/**
 *
 * @author N76VB
 */
public interface PersisntanceEntityCreator<T extends PersistenceEntity> {

    void create(T persistenceEntity);
}
