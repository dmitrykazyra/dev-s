/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.entity.core.AbstractActionEntity;

/**
 *
 * @author N76VB
 */
@Deprecated
public interface ActionEntityCreator<T extends AbstractActionEntity> {

    void createEntity(T entity);

}
