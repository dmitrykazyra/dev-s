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
import com.kdg.fs24.references.tariffs.accretionscheme.TariffAccretionScheme;
import com.kdg.fs24.application.core.api.ObjectRoot;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import com.kdg.fs24.references.tariffs.kind.TariffKind;
import com.kdg.fs24.references.tariffs.serv.TariffServ;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import lombok.Data;

@Data
@Entity
//@MappedSuperclass
@Table(name = "TariffRates")
public class TariffRate extends ObjectRoot implements PersistenceEntity {

    @Id
    @Column(name = "rate_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rate_id")
    @SequenceGenerator(name = "seq_rate_id", sequenceName = "seq_rate_id", allocationSize = 1)
    private Integer rateId;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy="tariffRate")
//    private final Collection<? extends TariffRateRecordAbstract> calcRecords = ServiceFuncs.<TariffRateRecordAbstract>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
    @ManyToOne
    @JoinColumn(name = "tariff_plan_id", referencedColumnName = "tariff_plan_id")
    AbstractTariffPlan tariffPlan;

    @ManyToOne
    @JoinColumn(name = "tariff_serv_id", referencedColumnName = "tariff_serv_id")
    private TariffServ tariffServ;

    @ManyToOne
    @JoinColumn(name = "tariff_kind_id", referencedColumnName = "tariff_kind_id")
    private TariffKind tariffKind;

    @ManyToOne
    @JoinColumn(name = "tariff_scheme_id", referencedColumnName = "tariff_scheme_id")
    private TariffAccretionScheme tariffAccretionScheme;

    @Column(name = "rate_name")
    private String rateName;

    @Column(name = "actual_date")
    private LocalDate actualDate;

    @Column(name = "close_date")
    private LocalDate closeDate;
    //==========================================================================

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tariffRate")
    private Collection<TariffRate_1> tariffRates_1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tariffRate")
    private Collection<TariffRate_2> tariffRates_2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tariffRate")
    private Collection<TariffRate_3> tariffRates_3;

    public Collection<TariffRecordAbstract> getTariffRates() {
        return null;
    }

    //==========================================================================
}
