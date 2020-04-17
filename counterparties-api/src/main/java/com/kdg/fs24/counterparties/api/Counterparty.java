/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.counterparties.api;

import javax.persistence.*;
import lombok.Data;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import com.kdg.fs24.entity.core.api.EntityKindId;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import com.kdg.fs24.entity.core.api.ActionClassesPackages;

/**
 *
 * @author N76VB
 */
@Entity
@Table(name = "core_Counterparties")
@Data
@PrimaryKeyJoinColumn(name = "counterparty_id", referencedColumnName = "entity_id")
@ActionClassesPackages(pkgList = {"com.kdg.fs24.counterparties.actions"})
@EntityTypeId(entity_type_id = CounterpartyConst.FS24_CLIENT,
        entity_type_name = "Клиент")
@EntityKindId(entity_kind_id = CounterpartyConst.FS24_ODINARY_CLIENT,
        entity_type_id = CounterpartyConst.FS24_CLIENT,
        entity_kind_name = "Стандартный клиент")
public class Counterparty extends AbstractActionEntity {

//    @Column(name = "counterparty_id")
//    private Long counterpartyId;
    @Column(name = "counterparty_code")
    private String counterpartyCode;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "full_name")
    private String fullName;

    public Long getCounterpartyId() {
        return super.getEntity_id();
    }
}
