/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.documents.api;

/**
 *
 * @author N76VB
 */
import com.kdg.fs24.references.api.DocAttrValue;
import com.kdg.fs24.references.documents.docstatus.DocStatus;
import com.kdg.fs24.references.documents.doctemplate.DocTemplate;
import java.util.Collection;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Document {

    int getLiasOperHC();
    
//    void setLiasOperHash(int liasOperHash);
    
    DocStatus getDoc_status();

    DocTemplate getDoc_template();

    Collection<DocAttrValue> getDoc_attrs();

    LocalDate getDoc_date();

    LocalDate getClose_date();
    
    void setDocId(Long doc_id);
    
    Long getDocId();

//    LocalDateTime getServer_date();

}
