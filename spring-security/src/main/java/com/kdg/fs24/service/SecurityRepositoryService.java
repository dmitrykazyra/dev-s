/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.application.core.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdg.fs24.spring.core.api.ApplicationRepositoryService;
import com.kdg.fs24.repository.*;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author N76VB
 */
@Data
@Service
public class SecurityRepositoryService implements ApplicationRepositoryService {

    @Bean
    public CommandLineRunner cliUserRepository(final ApplicationUserRepository applicationUserRepository) {
        return (args) -> {

            // final Collection<KeyWordRecords> op2 = keyWordsRepository.findKeyWords();
//            LogService.LogInfo(this.getClass(), () -> String.format("SecurityService4Test:ApplicationUserRepository: %d entries ",
//                    applicationUserRepository.count()));

        };
    }
//    @Autowired(required = false)
//    private ApplicationRoleRepository applicationRoleRepository;

//    @Autowired
//    private EntityTypesRepository entityTypesRepository;
//
//    @Autowired
//    private EntityStatusesRepository entityStatusesRepository;
}
