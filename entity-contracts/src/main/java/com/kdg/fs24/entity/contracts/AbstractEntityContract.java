/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.contracts;

import com.kdg.fs24.entity.core.AbstractActionEntity;
import javax.persistence.*;
import com.kdg.fs24.entity.contract.subjects.ContractSubject;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "core_EntityContracts")
@PrimaryKeyJoinColumn(name = "contract_id", referencedColumnName = "entity_id")
public class AbstractEntityContract extends AbstractActionEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JoinColumn(name = "entity_type_id", updatable = false, insertable = true)
    @JoinColumns({
        @JoinColumn(name = "contract_subject_id", referencedColumnName = "contract_subject_id")})
    private ContractSubject contractSubject;
    @Column(name = "contract_num")
    private String contractNum;
}
