/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.docattr;

import com.kdg.fs24.references.api.ReferenceRec;
import java.util.Map;
import com.kdg.fs24.references.api.AbstractRefRecord;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "docAttrsRef")
public class DocAttr extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "doc_attr_id")
    private Integer docAttrId;
    @Column(name = "doc_attr_code")
    private String docAttrCcode;
    @Column(name = "doc_attr_name")
    private String docAttrName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        //map.put(String.format("%s - %s", this.getDoc_attr_code(), this.getDoc_attr_name()), 0);
    }
}
