/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.docstatus;

import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import java.util.Map;
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

}
