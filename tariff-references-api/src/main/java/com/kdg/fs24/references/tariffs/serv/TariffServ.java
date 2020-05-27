/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.serv;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.references.api.AbstractRefRecord;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.ReferenceSyncOrder;
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
public class TariffServ extends AbstractRefRecord implements ReferenceRec  {
    @Id
    @Column(name="tariff_serv_id")
    private Integer tariffServId;
    @Column(name="tariff_group_id")
    private Integer tariffGroupId;
    @Column(name="tariff_serv_name")
    private String tariffServName;
    @Column(name="client_pay")
    private Boolean clientPay;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.tariffServId, this.tariffServName), this.getTariffServId());
    }
    public static <T extends TariffServ> Collection<T> getActualReferencesList() {

        final Collection<T> actualList = ServiceFuncs.<T>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
//        final Class<T> clazz = (Class<T>) (DocStatus.class);
//
//        actualList.add((T) NullSafe.<T>createObject(clazz, (object) -> {
//            object.setDocStatusId(DocumentsConst.DS_CREATED);
//            object.setDocStatusName(AbstractRefRecord.getTranslatedValue(new LangStrValue("Created", "Создан")));
//        }));
//        actualList.add((T) NullSafe.<T>createObject(clazz, (object) -> {
//            object.setDocStatusId(DocumentsConst.DS_CANCELLED);
//            object.setDocStatusName(AbstractRefRecord.getTranslatedValue(new LangStrValue("Cancelled", "Аннулирован")));
//        }));
//        actualList.add((T) NullSafe.<T>createObject(clazz, (object) -> {
//            object.setDocStatusId(DocumentsConst.DS_POSTPONED);
//            object.setDocStatusName(AbstractRefRecord.getTranslatedValue(new LangStrValue("Postponed", "Отложен")));
//        }));
//        actualList.add((T) NullSafe.<T>createObject(clazz, (object) -> {
//            object.setDocStatusId(DocumentsConst.DS_EXECUTED);
//            object.setDocStatusName(AbstractRefRecord.getTranslatedValue(new LangStrValue("Executed", "Исполнен")));
//        }));

        return actualList;
    } 
}
