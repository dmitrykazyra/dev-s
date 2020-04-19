/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.doctemplate;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
//import com.kdg.fs24.entity.contract.api.EntityContract;


/**
 *
 * @author kazyra_d
 */
public class DocTemplate extends AbstractRefRecord implements ReferenceRec {

    /**
     * @return the docTemplateGroup
     */
    private Integer doc_template_id;
    //private DocTemplateGroup docTemplateGroup;
    private Integer doc_template_group_id;
    private String doc_template_code;
    private String doc_template_name;
    private Integer pmt_sys_id;
    private Integer doc_type_id;

    public DocTemplate() {
        super();
    }

    //==========================================================================
    //public abstract Collection<DocAttrValue> createDocAttrs(final EntityContract entityContract, LiasOperInfo liasOperInfo);
    
    public Integer getDoc_template_id() {
        return doc_template_id;
    }

    public DocTemplate setDoc_template_id(final Integer doc_template_id) {
        this.doc_template_id = doc_template_id;
        return this;
    }

    public Integer getDoc_template_group_id() {
        return doc_template_group_id;
    }

    public DocTemplate setDoc_template_group_id(final Integer doc_template_group_id) {
        this.doc_template_group_id = doc_template_group_id;
        return this;
    }

    public String getDoc_template_code() {
        return doc_template_code;
    }

    public DocTemplate setDoc_template_code(final String doc_template_code) {
        this.doc_template_code = doc_template_code;
        return this;
    }

    public String getDoc_template_name() {
        return doc_template_name;
    }

    public DocTemplate setDoc_template_name(final String doc_template_name) {
        this.doc_template_name = doc_template_name;
        return this;
    }

    public Integer getPmt_sys_id() {
        return pmt_sys_id;
    }

    public DocTemplate setPmt_sys_id(final Integer pmt_sys_id) {
        this.pmt_sys_id = pmt_sys_id;
        return this;
    }

    public Integer getDoc_type_id() {
        return doc_type_id;
    }

    public DocTemplate setDoc_type_id(final Integer doc_type_id) {
        this.doc_type_id = doc_type_id;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getDoc_template_id(), this.getDoc_template_code()), this.getDoc_template_id());
    }

//    public DocTemplateGroup getDocTemplateGroup() {
//        return docTemplateGroup;
//    }
//
//    public void setDocTemplateGroup(DocTemplateGroup docTemplateGroup) {
//        this.docTemplateGroup = docTemplateGroup;
//    }
    
    //==========================================================================
    
}
