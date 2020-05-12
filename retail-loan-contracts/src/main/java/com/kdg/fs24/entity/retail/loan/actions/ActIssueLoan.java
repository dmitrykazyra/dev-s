/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.retail.loan.actions;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.entity.contracts.actions.AbstractLiasContractOper;
import com.kdg.fs24.entity.retail.loan.contracts.AbstractRetailLoanContract;
import com.kdg.fs24.entity.retail.loan.contracts.RetailLoanConstants;
import com.kdg.fs24.entity.core.api.ActionCodeId;
import com.kdg.fs24.lias.opers.attrs.*;
import com.kdg.fs24.lias.opers.napi.LiasFinanceOper;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import java.time.LocalDate;
import java.math.BigDecimal;
import lombok.Data;
import com.kdg.fs24.references.api.LiasesConst;

/**
 *
 * @author N76VB
 */
@Data
@ActionCodeId(action_code = RetailLoanConstants.ACT_ISSUE_LOAN,
        action_name = "Выдача кредита")
public class ActIssueLoan extends AbstractLiasContractOper<AbstractRetailLoanContract> {

    // сумма выдачи кредита
    private BigDecimal liasSum;

    @Override
    public void initialize() {
        // запрос даты расчета операции        
        //this.setAccretionDate(null);
        super.initialize();

        this.setLiasDate(LocalDate.now());
        this.setLiasSum(BigDecimal.valueOf(3.14 * 2));
    }

    //==========================================================================
    @Override
    protected void preCalculation() {

        final LiasFinanceOper liasFinanceOper = NullSafe.createObject(LiasFinanceOper.class);

        liasFinanceOper.<LIAS_SUMM>add(() -> this.getContractEntity().getContractSumm());

        //this.addNewLiasOper(new NewLiasOperImpl()
        liasFinanceOper.<LIAS_SUMM>add(() -> this.getContractEntity().getContractSumm());
        liasFinanceOper.<LIAS_CURRENCY_ID>add(() -> this.getContractEntity().getCurrency().getCurrencyId());
        liasFinanceOper.<COUNTERPARTY_ID>add(() -> this.getContractEntity().entityId());
        liasFinanceOper.<LIAS_DATE>add(() -> this.getLiasDate());
        liasFinanceOper.<DEBT_STATE_ID>add(() -> LiasesConst.LDS_NORMAL_DEBTS);
        liasFinanceOper.<LIAS_FINOPER_CODE>add(() -> LiasesConst.FOC_MAIN_PLACEMENT);
        liasFinanceOper.<LIAS_ACTION_TYPE_ID>add(() -> LiasesConst.LAT_GET_PRIMARY_LIASES);
        liasFinanceOper.<LIAS_KIND_ID>add(() -> LiasesConst.LKI_RETURN_MAIN_DEBT);
        liasFinanceOper.<LIAS_TYPE_ID>add(() -> LiasesConst.LTI_CURRENT_LIASES);
        liasFinanceOper.<LIAS_BASE_ASSET_TYPE_ID>add(() -> LiasesConst.LBAT_MONEYS);
        liasFinanceOper.<LIAS_START_DATE>add(() -> this.getContractEntity().getContractDate());
        liasFinanceOper.<LIAS_FINAL_DATE>add(() -> this.getContractEntity().getEndDate());
//                liasFinanceOper.<PMT_SCHEDULE>add(()
//                        -> ServiceFuncs.<PmtSchedule>getCollectionElement(
//                        this.getContractEntity().getPmtSchedules(),
//                        bs -> (bs.getEntityKindId().equals(BondScheduleConst.EK_BONDSCHEDULE_MAIN_DEBT)),
//                        String.format("BondSchedule is not found(%d)", BondScheduleConst.EK_BONDSCHEDULE_MAIN_DEBT)));
        liasFinanceOper.<OPER_NOTES>add(() -> "Issue loann");
        
        this.addNewLiasOper(liasFinanceOper);
        
        // ещё какая-то задолженность
//        this.addNewLiasOper(new NewLiasOperImpl()
//                .<LIAS_SUMM>add(() -> BigDecimal.valueOf(100))
//                .<LIAS_CURRENCY_ID>add(() -> this.getContractEntity().getContractCurrency_id())
//                .<COUNTERPARTY_ID>add(() -> this.getContractEntity().getCounterparty_id())
//                .<LIAS_DATE>add(() -> this.getLiasDate())
//                .<DEBT_STATE_ID>add(() -> LiasesConst.LDS_NORMAL_DEBTS)
//                .<LIAS_FINOPER_CODE>add(() -> LiasesConst.FOC_1)
//                .<LIAS_ACTION_TYPE_ID>add(() -> LiasesConst.LAT_GET_PRIMARY_LIASES)
//                .<LIAS_KIND_ID>add(() -> LiasesConst.LKI_2)
//                .<LIAS_TYPE_ID>add(() -> LiasesConst.LTI_CURRENT_LIASES)
//                .<LIAS_BASE_ASSET_TYPE_ID>add(() -> LiasesConst.LBAT_MONEYS)
//                .<LIAS_START_DATE>add(() -> this.getContractEntity().getContract_date())
//                .<LIAS_FINAL_DATE>add(() -> this.getContractEntity().getEnd_date())
//                .<OPER_NOTES>add(() -> "Создание Х задолженности")
//        );
        // создание платежного документа
        //this.addDocument(document);        
    }

}
