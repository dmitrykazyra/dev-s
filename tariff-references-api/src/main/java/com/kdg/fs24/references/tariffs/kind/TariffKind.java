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
import com.kdg.fs24.references.tariffs.accretionscheme.TariffAccretionScheme;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "TariffKindsRef")
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

    public static <T extends TariffKind> Collection<T> getActualReferencesList() {

        final Collection<T> actualList = ServiceFuncs.<T>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
        final Class<T> clazz = (Class<T>) (TariffKind.class);

        ReflectionFuncs.createPkgClassesCollection("com.kdg.fs24.entity", clazz)
                .stream()
                .filter(p -> !p.isInterface())
                .filter(p -> !Modifier.isAbstract(p.getModifiers()))
                .filter(p -> AnnotationFuncs.isAnnotated(p, TariffKindId.class))
                .forEach((refClazz) -> {

                    final TariffKindId tariffKindId = AnnotationFuncs.getAnnotation(refClazz, TariffKindId.class);

                    actualList.add((T) NullSafe.<T>createObject(clazz, (object) -> {
                        object.setTariffKindId(tariffKindId.tariff_kind_id());
                        object.setTariffKindName(AbstractRefRecord.getTranslatedValue(
                                new LangStrValue(tariffKindId.en_kind_name(), tariffKindId.tariff_kind_name())));
                        //object.setTariffServ(NullSafe.createObject(tariffKindId.tariff_serv_class()));

                        object.setTariffServ(AbstractRefRecord.<TariffServ>getRefeenceRecord(TariffServ.class,
                                record -> record.getTariffServId().equals(tariffKindId.tariff_serv_id())));

                    }));
                });
        return actualList;
    }
    //==========================================================================
    public final static TariffKind findTariffKind(final Integer tariffKindId) {
        return AbstractRefRecord.<TariffKind>getRefeenceRecord(TariffKind.class,
                record -> record.getTariffKindId().equals(tariffKindId));
    }
}
