/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.tariff;

/**
 *
 * @author N76VB
 */
import com.kdg.fs24.references.tariffs.kind.TariffRateRecord;
import com.kdg.fs24.references.tariffs.accretionscheme.TariffAccretionScheme;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.api.ObjectRoot;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import java.util.Collection;
import com.kdg.fs24.references.tariffs.kind.TariffKind;
import com.kdg.fs24.references.tariffs.serv.TariffServ;
import javax.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
//@Table(name = "TariffRates")
public class TariffRate_1 extends TariffRateAbstract {

    @OneToMany(cascade = CascadeType.ALL)
    private final Collection<TariffRateRecord_1> calcRecords = ServiceFuncs.<TariffRateRecord_1>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
}
