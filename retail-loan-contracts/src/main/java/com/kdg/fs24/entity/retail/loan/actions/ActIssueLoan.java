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
import java.time.LocalDate;
import java.util.Optional;
import java.math.BigDecimal;
import lombok.Data;
import com.kdg.fs24.references.api.LiasesConst;
import com.kdg.fs24.bond.schedule.api.BondScheduleConst;
import com.kdg.fs24.entity.core.api.RefreshEntity;

/**
 *
 * @author N76VB
 */
@Data
@ActionCodeId(action_code = RetailLoanConstants.ACT_ISSUE_LOAN,
        action_name = "Выдача кредита")
public class ActIssueLoan extends AbstractLiasContractOper<AbstractRetailLoanContract> {

    // сумма выдачи кредита
    private BigDecimal liasIssueSum;

    @Override
    public void initialize() {
        // запрос даты расчета операции        
        //this.setAccretionDate(null);
        super.initialize();

        //this.setLiasDate(LocalDate.now());
        //this.setLiasSum(BigDecimal.valueOf(3.14 * 2));
    }

    //==========================================================================
    @Override
    protected void preCalculation() {
        this.addNewLiasOper(NullSafe.createObject(LiasFinanceOper.class)
                .<LIAS_SUMM>addAttr(() -> this.getLiasIssueSum())
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
                .<OPER_NOTES>addAttr(() -> "Issue loann")
        );

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
