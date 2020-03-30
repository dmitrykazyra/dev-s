/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.common;

import com.kdg.fs24.references.core.AbstractReferencesList;
import com.kdg.fs24.references.application.application.ApplicationsRef;
import com.kdg.fs24.references.application.currency.CurrenciesRef;
import com.kdg.fs24.references.application.functions.FunctionsRef;
import com.kdg.fs24.exception.references.ReferenceNotFound;
import com.kdg.fs24.application.core.log.LogService;
//import com.kdg.fs24.services.api.Service;
import com.kdg.fs24.application.core.nullsafe.NullSafe;

/**
 *
 * @author kazyra_d
 */
public class AppReferencesListService extends AbstractReferencesList {

    public AppReferencesListService() {
        super();

    }

//    @Override
//    public void initializeService() {
//        this.registerPackageReferences("com.kdg.fs24.references.application");
//        Service.super.initializeService();
//    }

    public <S extends CurrenciesRef> S getCurrenciesRef() {
        return ((S) this.<S>findOrCreateReference(CurrenciesRef.class));
    }

    public <T, S extends CurrenciesRef> T getCurrency(final Integer currCode) {
        return (T) NullSafe.create()
                .execute2result(() -> {
                    return ((S) this.<S>findOrCreateReference(CurrenciesRef.class)).getCurrency(currCode);
                }).<T>getObject();
    }

    public <T, S extends CurrenciesRef> T getCashedCurrency(final Integer currCode) throws ReferenceNotFound {
        return (T) ((S) this.<S>findOrCreateReference(CurrenciesRef.class)).getCachedCurrency(currCode);
    }

    // Интерфесы для доступа к конкретным справочникам
    public <S extends CurrenciesRef> String getCurrencyName(final Integer currCode) throws ReferenceNotFound {
        return ((S) this.<S>findOrCreateReference(CurrenciesRef.class)).getCurrencyNameById(currCode);
    }

    public <S extends CurrenciesRef> String getCashedCurrencyName(final Integer currCode) throws ReferenceNotFound {
        return ((S) this.<S>findOrCreateReference(CurrenciesRef.class)).getCachedCurrencyNameById(currCode);
    }

    // приложениe (справочник приложений - ApplicationsRef)
    public <T, S extends ApplicationsRef> T getApplication(final Integer app_id) throws ReferenceNotFound {

        return (T) ((S) this.<S>findOrCreateReference(ApplicationsRef.class)).getApplicationById(app_id);
    }

    // имя приложения (справочник приложений - ApplicationsRef)
    public <S extends ApplicationsRef> String getAppNameById(final Integer app_id) throws ReferenceNotFound {

        return ((S) this.<S>findOrCreateReference(ApplicationsRef.class)).getAppNameById(app_id);
    }

    // имя приложения (справочник приложений - ApplicationsRef)
    public <S extends ApplicationsRef> String getAppNameByCode(final String appCode) throws ReferenceNotFound {

        return ((S) this.<S>findOrCreateReference(ApplicationsRef.class)).getAppNameByCode(appCode);
    }

    // имя приложения (справочник приложений - ApplicationsRef)
    public <S extends ApplicationsRef> String getAppUrlByCode(final String appCode) throws ReferenceNotFound {

        return ((S) this.<S>findOrCreateReference(ApplicationsRef.class)).getAppUrlByCode(appCode);
    }

    // url приложения (справочник приложений - ApplicationsRef)
    public <S extends ApplicationsRef> String getAppUrlById(final Integer appId) throws ReferenceNotFound {

        return ((S) this.<S>findOrCreateReference(ApplicationsRef.class)).getAppUrlById(appId);
    }

    // наименование функции (справочник функций - FunctionsRef)
    public <S extends FunctionsRef> String getFuncNameById(final Integer func_id) throws ReferenceNotFound {

        return ((S) this.<S>findOrCreateReference(FunctionsRef.class)).getFuncNameById(func_id);
    }
}
