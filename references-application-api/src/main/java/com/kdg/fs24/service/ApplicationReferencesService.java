/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import com.kdg.fs24.spring.core.api.ApplicationRepositoryService;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import com.kdg.fs24.references.application.currency.Currency;

/**
 *
 * @author N76VB
 */
@Data
@Service
public class ApplicationReferencesService implements ApplicationRepositoryService {

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    //==========================================================================
    public final void createCurrency(final Integer currencyId,
            final String currencyIso, final String currencyName) {

        persistanceEntityManager.<Currency>mergePersistenceEntity(Currency.class,
                currency -> {

                    currency.setCurrencyId(currencyId);
                    currency.setCurrencyIso(currencyIso);
                    currency.setCurrencyName(currencyName);

                });
    }
}
