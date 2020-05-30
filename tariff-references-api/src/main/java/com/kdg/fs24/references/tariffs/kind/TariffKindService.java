/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.references.tariffs.serv.TariffServ;

/**
 *
 * @author N76VB
 */
public abstract class TariffKindService<TS extends TariffServ, E extends ActionEntity, TR extends TariffRateRecord, TB extends TariffBox> 
        extends TariffKind {

}
