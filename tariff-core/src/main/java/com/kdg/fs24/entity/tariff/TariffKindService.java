/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.tariff;

import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.references.tariffs.serv.TariffServ;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.GenericFuncs;
import com.kdg.fs24.references.tariffs.kind.TariffBox;
import com.kdg.fs24.references.tariffs.kind.TariffKind;
import com.kdg.fs24.references.tariffs.kind.TariffRateRecord;
import java.time.LocalDate;
import java.util.Collection;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
//@Deprecated
public abstract class TariffKindService<TS extends TariffServ, E extends ActionEntity, TR extends TariffRateRecord, TB extends TariffBox>
        extends TariffKind {

    //private TariffRate<TR> tariffRate;
    //==========================================================================
    public TB buildTariffBox() {

        return (TB) NullSafe.createObject(GenericFuncs.getTypeParameterClass(this.getClass(), 3));
    }
    //==========================================================================
//        public TariffRate<TR> getTariffRate() {
//
//        return NullSafe.create(this.tariffRate)
//                .whenIsNull(() -> {
//                    this.tariffRate = NullSafe.createObject(TariffRateImpl.class, tariff_serv_id);
//                    this.tariffRate.setKind_id(tariff_kind_id);
//                    return this.tariffRate;
//                }).<TariffRate<TR>>getObject();
//    }
    //==========================================================================
    public abstract Collection<TariffCalcSum> calculateTariff(
            final E entityContract, 
            final TariffRate tariffRate,
            final LocalDate D1, 
            final LocalDate D2);
}
