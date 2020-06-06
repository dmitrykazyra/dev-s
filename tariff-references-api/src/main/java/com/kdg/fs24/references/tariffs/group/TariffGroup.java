/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.group;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.ReferenceSyncOrder;
import java.util.Map;
import com.kdg.fs24.references.api.AbstractRefRecord;
import com.kdg.fs24.references.api.DocumentsConst;
import com.kdg.fs24.references.api.LangStrValue;
import java.util.Collection;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "TariffGroupsRef")
@ReferenceSyncOrder(order_num = 1)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TariffGroup extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "tariff_group_id")
    private Integer tariffGroupId;
    @Column(name = "tariff_group_name")
    private String tariffGroupName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        //map.put(String.format("%d - %s", this.getTariff_group_id(), this.getTariff_group_name()), this.getTariff_group_id());
    }

    //==========================================================================
    public final static TariffGroup findTariffGroup(final Integer tariffGroupId) {
        return AbstractRefRecord.<TariffGroup>getRefeenceRecord(TariffGroup.class,
                record -> record.getTariffGroupId().equals(tariffGroupId));
    }
    
    public static <T extends TariffGroup> Collection<T> getActualReferencesList() {

        final Collection<T> actualList = ServiceFuncs.<T>createCollection();
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
