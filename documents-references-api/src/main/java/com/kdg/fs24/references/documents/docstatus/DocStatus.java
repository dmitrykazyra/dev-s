/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.docstatus;

import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

import java.util.Map;

/**
 *
 * @author kazyra_d
 */
public class DocStatus  extends AbstractRefRecord implements ReferenceRec {

    private Integer doc_status_id;
    private String doc_status_name;

    public DocStatus() {
        super();
    }

    //==========================================================================
    public Integer getDoc_status_id() {
        return doc_status_id;
    }

    public DocStatus setDoc_status_id(final Integer doc_status_id) {
        this.doc_status_id = doc_status_id;
        return this;
    }

    public String getDoc_status_name() {
        return doc_status_name;
    }

    public DocStatus setDoc_status_name(final String doc_status_name) {
        this.doc_status_name = doc_status_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getDoc_status_id(), this.getDoc_status_name()), this.getDoc_status_id());
    }

}
