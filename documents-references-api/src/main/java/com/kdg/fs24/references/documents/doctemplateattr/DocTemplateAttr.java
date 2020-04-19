/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.doctemplateattr;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class DocTemplateAttr extends AbstractRefRecord implements ReferenceRec {

    private Integer doc_template_id;
    private Integer doc_attr_id;

    public DocTemplateAttr() {
        super();
    }

    //==========================================================================
    public Integer getDoc_template_id() {
        return doc_template_id;
    }

    public DocTemplateAttr setDoc_template_id(final Integer doc_template_id) {
        this.doc_template_id = doc_template_id;
        return this;
    }

    public Integer getDoc_attr_id() {
        return doc_attr_id;
    }

    public DocTemplateAttr setDoc_attr_id(final Integer doc_attr_id) {
        this.doc_attr_id = doc_attr_id;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        //map.put(String.format("%s - %s", this.getDoc_attr_id(), this.getIs_mandatory()), 0);
    }

}
