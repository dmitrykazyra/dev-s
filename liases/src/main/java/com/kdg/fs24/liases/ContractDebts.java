/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.liases;

import com.kdg.fs24.application.core.service.funcs.FilterComparator;
import com.kdg.fs24.lias.opers.napi.NewLiasOper;
import com.kdg.fs24.lias.opers.attrs.*;
import com.kdg.fs24.liases.api.LiasDebt;
//import com.kdg.fs24.liases.references.LiasesReferencesService;
//import com.kdg.fs24.references.common.AppReferencesListService;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import java.util.Collection;
import java.time.LocalDate;
import com.kdg.fs24.liases.exception.LiasDebtDoesNotExists;
import com.kdg.fs24.application.core.api.ObjectRoot;
import java.math.BigDecimal;
import com.kdg.fs24.documents.api.Document;
import com.kdg.fs24.references.tariffs.kind.TariffAccretionHisory;
import com.kdg.fs24.lias.opers.api.LiasOpersConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
public class ContractDebts
        extends ObjectRoot { //implements ContractDebts<T> {

    private Long entity_id;
    private Long counterparty_id;

    private Collection<LiasDebt> contactDebts;
    private Collection<TariffAccretionHisory> accretionHistory;
}
