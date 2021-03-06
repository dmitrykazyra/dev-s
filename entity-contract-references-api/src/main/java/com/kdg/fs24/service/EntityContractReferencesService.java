/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import com.kdg.fs24.entity.contract.subjects.ContractSubject;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import com.kdg.fs24.spring.core.api.ApplicationService;

/**
 *
 * @author N76VB
 */
@Data
@Service
public class EntityContractReferencesService implements ApplicationService {

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    //==========================================================================
    public final void createContractSubject(final Integer contractSubjectId,
            final String contractSubjectName) {

        persistanceEntityManager.<ContractSubject>mergePersistenceEntity(ContractSubject.class,
                contractSubject -> {

                    contractSubject.setContractSubjectId(contractSubjectId);
                    contractSubject.setContractSubjectName(contractSubjectName);

                });
    }
}
