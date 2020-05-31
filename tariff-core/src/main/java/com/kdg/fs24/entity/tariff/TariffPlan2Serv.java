/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.tariff;

/**
 *
 * @author N76VB
 */
import com.kdg.fs24.persistence.api.PersistenceEntity;
import com.kdg.fs24.references.tariffs.serv.TariffServ;
import com.kdg.fs24.references.tariffs.kind.TariffKind;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TariffPlan2ServId")
@IdClass(TariffPlan2ServPK.class)
public class TariffPlan2Serv implements PersistenceEntity {

    @Id
    @ManyToOne
    @JoinColumn(name="tariff_plan_id")
    private AbstractTariffPlan tariffPlan;
    
    @Id
    @ManyToOne
    @JoinColumn(name="tariff_serv_id")
    private TariffServ tariffServ;

    @ManyToOne
    @JoinColumn(name="tariff_kind_id")
    private TariffKind tariffKind;

    @Column(name = "actual_date")
    private LocalDate actualDate;

    @Column(name = "close_date")
    private LocalDate closeDate;

}
