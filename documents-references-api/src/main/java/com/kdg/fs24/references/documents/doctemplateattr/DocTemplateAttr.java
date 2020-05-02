/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.doctemplateattr;

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
@Table(name = "docTemplateAttrsRef")
public class DocTemplateAttr extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "doc_template_id")
    private Integer docTemplateId;
    @Column(name = "doc_attr_id")
    private Integer docAttrId;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        //map.put(String.format("%s - %s", this.getDoc_attr_id(), this.getIs_mandatory()), 0);
    }

}
