/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.retail.loan.contracts;

import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.entity.core.api.ActionClassesPackages;
import com.kdg.fs24.entity.core.api.DefaultEntityStatus;
import com.kdg.fs24.entity.core.api.EntityKindId;
import com.kdg.fs24.entity.core.api.EntityStatusesRef;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import com.kdg.fs24.entity.status.EntityStatusId;
import com.kdg.fs24.loan.references.api.LoanSource;
import com.kdg.fs24.bond.schedule.references.api.PmtScheduleAlg;
import com.kdg.fs24.bond.schedule.references.api.PmtScheduleTerm;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Entity
@Data
@Table(name = "rlc_loanContracts")
@PrimaryKeyJoinColumn(name = "contract_id", referencedColumnName = "contract_id")
@EntityTypeId(entity_type_id = RetailLoanConstants.LOAN2INDIVIDUAL,
        entity_type_name = "Кредиты физическим лицам")
@EntityKindId(entity_kind_id = RetailLoanConstants.LOAN2INDIVIDUAL_CARD,
        entity_type_id = RetailLoanConstants.LOAN2INDIVIDUAL,
        entity_kind_name = "Кредит ФЛ на карточку")
@EntityStatusesRef(
        entiy_status = {
            @EntityStatusId(
                    entity_type_id = RetailLoanConstants.LOAN2INDIVIDUAL,
                    entity_status_id = SysConst.ES_VALID,
                    entity_status_name = "Действующая сделка")
            ,
            @EntityStatusId(
                    entity_type_id = RetailLoanConstants.LOAN2INDIVIDUAL,
                    entity_status_id = SysConst.ES_CLOSED,
                    entity_status_name = "Закрытая сделка")
            ,
            @EntityStatusId(
                    entity_type_id = RetailLoanConstants.LOAN2INDIVIDUAL,
                    entity_status_id = SysConst.ES_CANCELLED,
                    entity_status_name = "Аннулированная сделка")
        })
@DefaultEntityStatus(entity_status = SysConst.ES_VALID)
@ActionClassesPackages(pkgList = {"com.kdg.fs24.retail.loan.actions",
    "com.kdg.fs24.entity.contracts.actions"})
public class RetailLoanContract extends AbstractRetailLoanContract {

    @ManyToOne
    @JoinColumn(name = "loan_source_id", referencedColumnName = "loan_source_id")
    private LoanSource loanSource;
    @ManyToOne
    @JoinColumn(name = "schedule_alg_id", referencedColumnName = "schedule_alg_id")
    private PmtScheduleAlg pmtScheduleAlg;
    @ManyToOne
    @JoinColumn(name = "pmt_term_id", referencedColumnName = "pmt_term_id")
    private PmtScheduleTerm pmtScheduleTerm;
}
