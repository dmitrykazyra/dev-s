/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.serv;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import com.kdg.fs24.references.api.LangStrValue;
import com.kdg.fs24.references.api.AbstractRefRecord;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.ReferenceSyncOrder;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "TariffServsRef")
@ReferenceSyncOrder(order_num = 1)
public class TariffServ extends AbstractRefRecord implements ReferenceRec {
    
    @Id
    @Column(name = "tariff_serv_id", updatable = false)
    private Integer tariffServId;
    @Column(name = "tariff_group_id")
    private Integer tariffGroupId;
    @Column(name = "tariff_serv_name")
    private String tariffServName;
    @Column(name = "client_pay")
    private Boolean clientPay;
    
    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.tariffServId, this.tariffServName), this.getTariffServId());
    }
    //==========================================================================
    public static <T extends TariffServ> Collection<T> getActualReferencesList() {
        
        final Collection<T> actualList = ServiceFuncs.<T>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
        final Class<T> clazz = (Class<T>) (TariffServ.class);
        
        ReflectionFuncs.createPkgClassesCollection("com.kdg.fs24.references", clazz)
                .stream()
                .filter(p -> !p.isInterface())
                .filter(p -> !Modifier.isAbstract(p.getModifiers()))
                .filter(p -> AnnotationFuncs.isAnnotated(p, TariffServId.class))
                .forEach((refClazz) -> {
                    
                    final TariffServId tariffServId = AnnotationFuncs.getAnnotation(refClazz, TariffServId.class);
                    
                    actualList.add((T) NullSafe.<T>createObject(clazz, (object) -> {
                        object.setTariffGroupId(tariffServId.group_id());
                        object.setTariffServName(AbstractRefRecord.getTranslatedValue(
                                new LangStrValue(tariffServId.en_serv_name(), tariffServId.serv_name())));
                        object.setTariffServId(tariffServId.serv_id());
                        object.setClientPay(tariffServId.client_pay());
                    }));
                });
        return actualList;
    }
}
