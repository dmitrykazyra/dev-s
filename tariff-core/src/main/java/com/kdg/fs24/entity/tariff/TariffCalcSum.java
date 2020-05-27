/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.tariff;

import com.kdg.fs24.application.core.api.ObjectRoot;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import javax.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "tariffCalcSum")
@IdClass(TariffCalcSumPK.class)
public class TariffCalcSum extends ObjectRoot implements PersistenceEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "tariff_calc_id", referencedColumnName = "tariff_calc_id")
    private TariffCalcRecord tariffCalcRecord;

    @Id
    @Column(name = "tariff_calc_date")
    private LocalDate tariffCalcDate;

    @Column(name = "tariff_summ")
    private BigDecimal tariffSumm;
}
