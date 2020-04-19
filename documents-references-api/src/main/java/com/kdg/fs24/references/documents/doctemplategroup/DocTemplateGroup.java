/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.doctemplategroup;


import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
/**
 *
 * @author kazyra_d
 */
public class DocTemplateGroup   extends AbstractRefRecord implements ReferenceRec {

    private Integer doc_template_group_id;
    private String doc_template_group_name;

    public DocTemplateGroup() {
        super();
    }

    public Integer getDoc_template_group_id() {
        return doc_template_group_id;
    }

    public DocTemplateGroup setDoc_template_group_id(final Integer doc_template_group_id) {
        this.doc_template_group_id = doc_template_group_id;
        return this;
    }

    public String getDoc_template_group_name() {
        return doc_template_group_name;
    }

    public DocTemplateGroup setDoc_type_name(final String doc_type_name) {
        this.doc_template_group_name = doc_type_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getDoc_template_group_id(), this.getDoc_template_group_name()), this.getDoc_template_group_id());
    }

}