/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.core.api;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author N76VB
 */
public interface ApplicationJpaRepository<T extends Object, ID extends Object>
        extends JpaRepository<T, ID>, ApplicationBean {

}
