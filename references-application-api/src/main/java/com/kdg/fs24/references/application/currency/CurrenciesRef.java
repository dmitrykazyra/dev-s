/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.application.currency;

import com.kdg.fs24.references.core.AbstractReference;
import com.kdg.fs24.references.api.RefClass;
import com.kdg.fs24.exception.references.ReferenceNotFound;
import com.kdg.fs24.application.core.nullsafe.NullSafe;

/**
 *
 * @author kazyra_d
 */
@Deprecated
@RefClass(reference_class = Currency.class)
public class CurrenciesRef<T extends Currency> extends AbstractReference<Currency> {

    //==========================================================================
    public T getCurrency(final Integer currency_id) throws ReferenceNotFound {

        return (T) this.<T>findReference(() -> (this.findCurrencyById(currency_id)),
                String.format("Неизвестный код валюты (%d)", currency_id));

    }

    //==========================================================================
    public String getCurrencyNameById(final Integer currency_id) throws ReferenceNotFound {

        return this.findCurrencyById(currency_id).getCurrencyName();
    }

    //==========================================================================
    public T getCachedCurrency(final Integer currency_id) throws ReferenceNotFound {

        return (T) findCurrencyById(currency_id);
    }

    //==========================================================================
    public String getCachedCurrencyNameById(final Integer currency_id) throws ReferenceNotFound {

        String result = "?";
        T currency = null;

        currency = this.getCachedCurrency(currency_id);;

        if (NullSafe.notNull(currency)) {
            result = currency.getCurrencyName();
        }

        return result;
    }

    //==========================================================================
    private T findCurrencyById(final Integer currency_id) {

        return (T) this.findCachedRecords((object) -> ((Currency) object).getCurrencyId().equals(currency_id));
    }

    //==========================================================================
    public static <T extends AbstractReference> void registerReference() {
        // сохраняем в бд записи справочника видов обязательств

//        synchronized (AbstractReference.class) {
//
//            final float ref_version = (float) 0.01;
//
//            AbstractReference.store(ref_version,
//                    () -> {
//                        new JCallBatch("{call tariffs_insertorupdate_TariffGroup(:TGI, :TGN)}")
//                                .execBatch(stmt -> {
//
//                                    for (TariffGroup tariffGroup : getTariffGroupReference()) {
//                                        stmt.setParamByName("TGI", tariffGroup.getTariff_group_id());
//                                        stmt.setParamByName("TGN", tariffGroup.getTariff_group_name());
//                                        stmt.addBatch();
//                                    }
//                                });
//                    });
//
//        }
    }
}
