/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.retail.tariff.kind;

/**
 *
 * @author N76VB
 */

import com.kdg.fs24.references.tariffs.api.TariffConst;
import com.kdg.fs24.references.tariffs.kind.TariffKindService;
import com.kdg.fs24.references.tariffs.kind.TariffKindId;
import com.kdg.fs24.entity.tariff.TariffRate_1;
import com.kdg.fs24.entity.calculations.TariffTurnOverBox;
import com.kdg.fs24.entity.retail.loan.contracts.AbstractRetailLoanContract;
import com.kdg.fs24.test.api.TestConst;
import com.kdg.fs24.references.serv.TariffServCashBack;


@TariffKindId(
        tariff_serv_class = TariffServCashBack.class,
        tariff_kind_id = TariffConst.TK_CASHBACK,
        tariff_kind_name = "КэшБэк (БИБ)",
        tariff_scheme_id = TariffConst.SCH_30_365)
public class TariffKindCashBack4BIB extends TariffKindService<TariffServCashBack, AbstractRetailLoanContract, TariffRate_1, TariffTurnOverBox> {
    
}
