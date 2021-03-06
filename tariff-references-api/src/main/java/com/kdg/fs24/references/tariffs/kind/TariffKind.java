/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.references.api.AbstractRefRecord;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.tariffs.serv.TariffServ;
import com.kdg.fs24.references.api.LangStrValue;
import com.kdg.fs24.references.api.ReferenceSyncOrder;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "TariffKindsRef")
@ReferenceSyncOrder(order_num = 1)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TariffKind extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "tariff_kind_id", updatable = false)
    private Integer tariffKindId;

    @ManyToOne
    @JoinColumn(name = "tariff_serv_id", referencedColumnName = "tariff_serv_id")
    private TariffServ tariffServ;

    @Column(name = "tariff_kind_name")
    private String tariffKindName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        //map.put(String.format("%d - %s", this.getTariff_group_id(), this.getTariff_group_name()), this.getTariff_group_id());
    }

    public static <T extends TariffKind, A extends TariffKindId> Collection<T> getActualReferencesList() {

        final Collection<T> actualList = ServiceFuncs.<T>createCollection();
        final Class<T> clazz = (Class<T>) (TariffKind.class);
        final Class annClazz = (Class<A>) TariffKindId.class;

        // значения для справочника берутся из аннотаций классов
        ReflectionFuncs.processPkgClassesCollection("com.kdg.fs24.entity", clazz, annClazz,
                (tkClass) -> {

                    final A ClassAnnotation = (A) AnnotationFuncs.getAnnotation(tkClass, annClazz);

                    // коллекция для персиста
                    actualList.add((T) NullSafe.<T>createObject(clazz, (object) -> {
                        object.setTariffKindId(ClassAnnotation.tariff_kind_id());
                        object.setTariffKindName(AbstractRefRecord.getTranslatedValue(new LangStrValue(ClassAnnotation.en_kind_name(), ClassAnnotation.tariff_kind_name())));
                        //object.setTariffServ(NullSafe.createObject(tariffKindId.tariff_serv_class()));

                        object.setTariffServ(AbstractRefRecord.<TariffServ>getRefeenceRecord(TariffServ.class,
                                record -> record.getTariffServId().equals(ClassAnnotation.tariff_serv_id())));
                    }));
                });

        return actualList;
    }

    //==========================================================================
    @Transient
    private final static Map<Integer, TariffKind> TARIFF_KIND_RECORDS = ServiceFuncs.<Integer, TariffKind>getOrCreateMap(ServiceFuncs.MAP_NULL);

    //==========================================================================
    public final static <T extends TariffKind, A extends TariffKindId> TariffKind findTariffKind(final Integer v_tariffKindId) {

        if (TARIFF_KIND_RECORDS.isEmpty()) {

            final Class<T> clazz = (Class<T>) (TariffKind.class);
            final Class annClazz = (Class<A>) TariffKindId.class;

            // значения для справочника берутся из аннотаций классов
            ReflectionFuncs.processPkgClassesCollection("com.kdg.fs24.entity", clazz, annClazz,
                    (tkClass) -> {

                        final A classAnnotation = (A) AnnotationFuncs.<A>getAnnotation(tkClass, annClazz);

                        TARIFF_KIND_RECORDS.put(classAnnotation.tariff_kind_id(), (T) NullSafe.<T>createObject(tkClass, (object) -> {
                            object.setTariffKindId(classAnnotation.tariff_kind_id());
                            object.setTariffKindName(AbstractRefRecord.getTranslatedValue(new LangStrValue(classAnnotation.en_kind_name(), classAnnotation.tariff_kind_name())));
                            //object.setTariffServ(NullSafe.createObject(tariffKindId.tariff_serv_class()));

                            object.setTariffServ(AbstractRefRecord.<TariffServ>getRefeenceRecord(TariffServ.class,
                                    record -> record.getTariffServId().equals(classAnnotation.tariff_serv_id())));

                        }));
                    });
        }
        return ServiceFuncs.getMapValue(TARIFF_KIND_RECORDS, mapEntry
                -> mapEntry.getKey().equals(v_tariffKindId))
                .orElseThrow(() -> new RuntimeException(String.format("TariffKind is not found (%d)", v_tariffKindId)));
    }
}
