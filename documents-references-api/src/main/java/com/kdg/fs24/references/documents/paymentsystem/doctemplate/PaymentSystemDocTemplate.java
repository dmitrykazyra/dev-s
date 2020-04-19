/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.paymentsystem.doctemplate;

import java.util.Map;
import java.time.LocalDate;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author N76VB
 */
public class PaymentSystemDocTemplate extends AbstractRefRecord implements ReferenceRec {

    private Integer pmt_sys_id;
    private Integer fin_oper_code;
    private Integer doc_template_id;
    private LocalDate actual_date;
    private LocalDate close_date;

    public PaymentSystemDocTemplate() {
        super();
    }

    public Integer getPmt_sys_id() {
        return pmt_sys_id;
    }

    public PaymentSystemDocTemplate setPmt_sys_id(final Integer pmt_sys_id) {
        this.pmt_sys_id = pmt_sys_id;
        return this;
    }

    public Integer getFin_oper_code() {
        return fin_oper_code;
    }

    public PaymentSystemDocTemplate setFin_oper_code(final Integer action_code) {
        this.fin_oper_code = action_code;
        return this;
    }

    public LocalDate getActual_date() {
        return actual_date;
    }

    public PaymentSystemDocTemplate setActual_date(final LocalDate actual_date) {
        this.actual_date = actual_date;
        return this;
    }

    public LocalDate getClose_date() {
        return close_date;
    }

    public PaymentSystemDocTemplate setClose_date(final LocalDate close_date) {
        this.close_date = close_date;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %d", this.getPmt_sys_id(), this.getFin_oper_code()), this.getPmt_sys_id());
    }

    public Integer getDoc_template_id() {
        return doc_template_id;
    }

    public PaymentSystemDocTemplate setDoc_template_id(final Integer doc_template_id) {
        this.doc_template_id = doc_template_id;
        return this;
    }
}
