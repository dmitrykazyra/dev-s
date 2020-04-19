/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.documents.paymentsystem;

/**
 *
 * @author kazyra_d
 */
import java.time.LocalDate;
import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

public class PaymentSystem extends AbstractRefRecord implements ReferenceRec {

    private Integer pmt_sys_id;
    private String pmt_sys_code;
    private String pmt_sys_name;
    private LocalDate pmt_open_date;
    private LocalDate pmt_close_date;

    public PaymentSystem() {
        super();
    }

    //==========================================================================
    public Integer getPmt_sys_id() {
        return pmt_sys_id;
    }

    public PaymentSystem setPmt_sys_id(final Integer pmt_sys_id) {
        this.pmt_sys_id = pmt_sys_id;
        return this;
    }

    public String getPmt_sys_code() {
        return pmt_sys_code;
    }

    public PaymentSystem setPmt_sys_code(final String pmt_sys_code) {
        this.pmt_sys_code = pmt_sys_code;
        return this;
    }

    public String getPmt_sys_name() {
        return pmt_sys_name;
    }

    public PaymentSystem setPmt_sys_name(final String pmt_sys_name) {
        this.pmt_sys_name = pmt_sys_name;
        return this;
    }

    public LocalDate getPmt_open_date() {
        return pmt_open_date;
    }

    public PaymentSystem setPmt_open_date(final LocalDate pmt_open_date) {
        this.pmt_open_date = pmt_open_date;
        return this;
    }

    public LocalDate getPmt_close_date() {
        return pmt_close_date;
    }

    public PaymentSystem setPmt_close_date(final LocalDate pmt_close_date) {
        this.pmt_close_date = pmt_close_date;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getPmt_sys_id(), this.getPmt_sys_name()), this.getPmt_sys_id());
    }

}
