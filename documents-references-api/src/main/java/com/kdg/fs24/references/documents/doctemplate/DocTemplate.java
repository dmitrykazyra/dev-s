/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.doctemplate;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "docTemplatesRef")
public class DocTemplate extends AbstractRefRecord implements ReferenceRec {

    /**
     * @return the docTemplateGroup
     */
    @Id
    @Column(name = "doc_template_id")
    private Integer docTemplateId;
    //private DocTemplateGroup docTemplateGroup;

    @Column(name = "doc_template_group_id")
    private Integer docTemplateGroupId;
    @Column(name = "doc_template_code")
    private String docTemplateCode;
    @Column(name = "doc_template_name")
    private String docTemplateName;
    @Column(name = "pmt_sys_id")
    private Integer pmtSysId;
    @Column(name = "doc_type_id")
    private Integer docTypeId;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        //map.put(String.format("%d - %s", this.getDoc_template_id(), this.getDoc_template_code()), this.getDoc_template_id());
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
