/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.doctype;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class DocType extends AbstractRefRecord implements ReferenceRec {

    private Integer doc_type_id;
    private String doc_type_name;

    public DocType() {
        super();
    }

    public Integer getDoc_type_id() {
        return doc_type_id;
    }

    public DocType setDoc_type_id(final Integer doc_type_id) {
        this.doc_type_id = doc_type_id;
        return this;
    }

    public String getDoc_type_name() {
        return doc_type_name;
    }

    public DocType setDoc_type_name(final String doc_type_name) {
        this.doc_type_name = doc_type_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getDoc_type_id(), this.getDoc_type_name()), this.getDoc_type_id());
    }

}
