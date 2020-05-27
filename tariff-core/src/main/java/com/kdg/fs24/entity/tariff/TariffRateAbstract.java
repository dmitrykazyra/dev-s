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
import com.kdg.fs24.references.tariffs.kind.TariffRateRecord;
import com.kdg.fs24.references.tariffs.accretionscheme.TariffAccretionScheme;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.api.ObjectRoot;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import java.util.Collection;
import com.kdg.fs24.references.tariffs.kind.TariffKind;
import com.kdg.fs24.references.tariffs.serv.TariffServ;
import javax.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Data
//@Entity
@MappedSuperclass
@Table(name = "TariffRates")
public class TariffRateAbstract extends ObjectRoot implements PersistenceEntity {

    @Id
    @Column(name = "rate_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rate_id")
    @SequenceGenerator(name = "seq_rate_id", sequenceName = "seq_rate_id", allocationSize = 1)
    private Integer rateId;

    @OneToMany(cascade = CascadeType.ALL)
    private final Collection<TariffRateRecord_1> calcRecords = ServiceFuncs.<TariffRateRecord_1>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
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
//    public TariffRate(final Integer serv_id) {
//        this();
//        this.setServ_id(serv_id);
//    }
    //==========================================================================
//    @Override
//    public void store() {
//
//        if (!getRateRecords().isEmpty()) {
//
//            NullSafe.create(GenericFuncs.getElementClass(this.getRateRecords()))
//                    .safeExecute((clazz) -> {
//
//                        NullSafe.create(((Class) clazz).getMethod(methodName, Collection.class, Integer.class))
//                                .whenIsNull(() -> LogService.LogErr(this.getClass(), () -> String.format("Method is not found in records collection (%s)", this.methodName)))
//                                .safeExecute(method -> {
//                                    ((Method) method).invoke(null, this.getRateRecords(), this.getRate_id());
//                                });
//                    });
//        }
//    }

//    public <TR extends TariffRateRecord> TariffRate<TR> addRateRecord(final TR rateRecord) {
//        calcRecords.add((T) rateRecord);
//        return (TariffRate<TR>) this;
//    }
//    public void printRates() {
//        //анонимный класс для принтования списка остатков
//        final CustomCollectionImpl customCollection = NullSafe.createObject(CustomCollectionImpl.class, String.format("RateRecords (rate_id=%d, %d records, rate_name={%s}, scheme_id = {%d})\n",
//                TariffRate.this.getRate_id(),
//                TariffRate.this.<T>getRateRecords().size(),
//                TariffRate.this.getRate_name(),
//                TariffRate.this.getTariff_scheme_id()));
//
//        (this.<T>getRateRecords())
//                .stream()
//                .sorted((r1, r2) -> {
//                    // сортировка по дате в списке
//                    return r1.getRate_date().compareTo(r2.getRate_date());
//                })
//                .forEach(rest -> {
//                    customCollection.addCustomRecord(() -> String.format("rateDate: %s; rate value: %f\n",
//                            NLS.getStringDate(rest.getRate_date()),
//                            rest.getRate_value()));
//                });
//
//        LogService.LogInfo(this.getClass(), () -> customCollection.getRecord());
//    }
}

