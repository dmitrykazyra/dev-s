/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.docstatus;

import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import java.util.Map;
import java.util.Collection;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.references.api.LangStrValue;
import com.kdg.fs24.references.api.DocumentsConst;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "docStatusesRef")
public class DocStatus extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "doc_status_id")
    private Integer docStatusId;

    @Column(name = "doc_status_name")
    private String docStatusName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        //map.put(String.format("%d - %s", this.getDoc_status_id(), this.getDoc_status_name()), this.getDoc_status_id());
    }

    //==========================================================================
    public final static DocStatus findDocStatus(final Integer statusId) {
        return AbstractRefRecord.<DocStatus>getRefeenceRecord(DocStatus.class,
                record -> record.getDocStatusId().equals(statusId));
    }

    //==========================================================================
    public static <T extends DocStatus> Collection<T> getActualReferencesList() {

        final Collection<T> actualList = ServiceFuncs.<T>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
        final Class<T> clazz = (Class<T>) (DocStatus.class);

        actualList.add((T) NullSafe.<T>createObject(clazz, (object) -> {
            object.setDocStatusId(DocumentsConst.DS_CREATED);
            object.setDocStatusName(AbstractRefRecord.getTranslatedValue(new LangStrValue("Created", "Создан")));
        }));
        actualList.add((T) NullSafe.<T>createObject(clazz, (object) -> {
            object.setDocStatusId(DocumentsConst.DS_CANCELLED);
            object.setDocStatusName(AbstractRefRecord.getTranslatedValue(new LangStrValue("Cancelled", "Аннулирован")));
        }));
        actualList.add((T) NullSafe.<T>createObject(clazz, (object) -> {
            object.setDocStatusId(DocumentsConst.DS_POSTPONED);
            object.setDocStatusName(AbstractRefRecord.getTranslatedValue(new LangStrValue("Postponed", "Отложен")));
        }));
        actualList.add((T) NullSafe.<T>createObject(clazz, (object) -> {
            object.setDocStatusId(DocumentsConst.DS_EXECUTED);
            object.setDocStatusName(AbstractRefRecord.getTranslatedValue(new LangStrValue("Executed", "Исполнен")));
        }));

        return actualList;
    }
}
