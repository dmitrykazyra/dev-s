/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.repository;

import com.kdg.fs24.spring.security.ApplicationUserImpl;
import com.kdg.fs24.spring.core.api.ApplicationJpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author N76VB
 */
@Repository
public interface ApplicationUserRepository extends ApplicationJpaRepository<ApplicationUserImpl, Long> {

    //public Integer findByPassword(String pwd);

}
