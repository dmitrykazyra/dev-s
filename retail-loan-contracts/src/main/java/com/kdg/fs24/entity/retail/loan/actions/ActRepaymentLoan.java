/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.retail.loan.actions;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.bond.schedule.api.BondScheduleConst;
import com.kdg.fs24.entity.contracts.actions.AbstractLiasContractOper;
import java.math.BigDecimal;
import com.kdg.fs24.entity.core.api.ActionCodeId;
import com.kdg.fs24.entity.retail.loan.contracts.AbstractRetailLoanContract;
import com.kdg.fs24.lias.opers.attrs.*;
import com.kdg.fs24.entity.retail.loan.contracts.RetailLoanConstants;
import com.kdg.fs24.lias.opers.napi.LiasFinanceOper;
import com.kdg.fs24.references.api.LiasesConst;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
@ActionCodeId(action_code = RetailLoanConstants.ACT_REPAYMENT_LOAN,
        action_name = "Погашение кредита")
public class ActRepaymentLoan extends AbstractLiasContractOper<AbstractRetailLoanContract> {

    // сумма выдачи кредита
    private BigDecimal liasRepaymentSum;

    //==========================================================================
    @Override
    protected void preCalculation() {
        this.addNewLiasOper(NullSafe.createObject(LiasFinanceOper.class)
                .<LIAS_SUMM>addAttr(() -> this.getLiasRepaymentSum())
                .<LIAS_CURRENCY_ID>addAttr(() -> this.getContractEntity().getCurrency().getCurrencyId())
                .<COUNTERPARTY_ID>addAttr(() -> this.getContractEntity().entityId())
                .<LIAS_DATE>addAttr(() -> this.getLiasDate())
                .<DEBT_STATE_ID>addAttr(() -> LiasesConst.LDS_NORMAL_DEBTS)
                .<LIAS_FINOPER_CODE>addAttr(() -> LiasesConst.FOC_MAIN_PLACEMENT)
                .<LIAS_ACTION_TYPE_ID>addAttr(() -> LiasesConst.LAT_GET_PRIMARY_LIASES)
                .<LIAS_KIND_ID>addAttr(() -> LiasesConst.LKI_RETURN_MAIN_DEBT)
                .<LIAS_TYPE_ID>addAttr(() -> LiasesConst.LTI_CURRENT_LIASES)
                .<LIAS_BASE_ASSET_TYPE_ID>addAttr(() -> LiasesConst.LBAT_MONEYS)
                .<LIAS_START_DATE>addAttr(() -> this.getContractEntity().getBeginDate())
                .<LIAS_FINAL_DATE>addAttr(() -> this.getContractEntity().getEndDate())
                .<PMT_SCHEDULE>addAttr(() -> BondScheduleConst.EK_BONDSCHEDULE_MAIN_DEBT)
                .<OPER_NOTES>addAttr(() -> "Repayment loann")
        );
    }

}
