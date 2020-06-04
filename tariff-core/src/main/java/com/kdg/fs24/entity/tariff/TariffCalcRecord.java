/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.tariff;

import com.kdg.fs24.application.core.api.ObjectRoot;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.core.AbstractPersistenceEntity;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import java.util.Collection;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "TariffCalcRecords")
public class TariffCalcRecord extends ObjectRoot implements PersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tariff_calc_id")
    @SequenceGenerator(name = "seq_tariff_calc_id", sequenceName = "seq_tariff_calc_id", allocationSize = 1)
    @Column(name = "tariff_calc_id")
    private Integer tariffCalcId;
    //--------------------------------------------------------------------------
    @ManyToOne
    private TariffRate tariffRate;
    //--------------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "entity_id", referencedColumnName = "entity_id")
    private AbstractPersistenceEntity entity;
    //--------------------------------------------------------------------------
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tariffCalcRecord")
    private Collection<TariffCalcSum> tariffSums = ServiceFuncs.<TariffCalcSum>createCollection();
}
