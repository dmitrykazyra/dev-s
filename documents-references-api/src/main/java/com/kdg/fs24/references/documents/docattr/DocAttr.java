/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.docattr;

import com.kdg.fs24.references.api.ReferenceRec;
import java.util.Map;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class DocAttr extends AbstractRefRecord implements ReferenceRec {

    private Integer doc_attr_id;
    private String doc_attr_code;
    private String doc_attr_name;

    public DocAttr() {
        super();
    }

    //==========================================================================
    public String getDoc_attr_code() {
        return doc_attr_code;
    }

    public DocAttr setDoc_attr_code(final String doc_attr_code) {
        this.doc_attr_code = doc_attr_code;
        return this;
    }

    public String getDoc_attr_name() {
        return doc_attr_name;
    }

    public DocAttr setDoc_attr_name(final String doc_attr_name) {
        this.doc_attr_name = doc_attr_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%s - %s", this.getDoc_attr_code(), this.getDoc_attr_name()), 0);
    }

    public Integer getDoc_attr_id() {
        return doc_attr_id;
    }

    public DocAttr setDoc_attr_id(final Integer doc_attr_id) {
        this.doc_attr_id = doc_attr_id;
        return this;
    }

}
