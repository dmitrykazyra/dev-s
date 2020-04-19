/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.tariff.core.api;

import com.kdg.fs24.entity.core.api.ActionEntity;
import java.util.Collection;
import com.kdg.fs24.references.tariffs.kind.TariffKind;

/**
 *
 * @author N76VB
 */
public interface TariffPlan extends ActionEntity {

    Collection<TariffKind> getTariffKinds();
    
    TariffKind getTariffKind(final Integer serv_id);

    TariffPlan addTariffKind(TariffKind tariffKind);

    TariffPlan addTariffKind(int tariffKind);

    TariffPlan createTariffKind(int tariffKind, TariffKindProcessor tkp);

}
