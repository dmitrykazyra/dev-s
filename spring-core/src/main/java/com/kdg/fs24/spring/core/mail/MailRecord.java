/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.core.mail;

import com.kdg.fs24.application.core.log.LogRecord;

/**
 *
 * @author N76VB
 */
public class MailRecord extends LogRecord {

    private String header;
    private String body;
    private String address;

    public MailRecord() {
        super();
    }

    //==========================================================================
    public String getHeader() {
        return header;
    }

    public MailRecord setHeader(final String header) {
        this.header = header;
        return this;
    }

    public String getBody() {
        return body;
    }

    public MailRecord setBody(final String body) {
        this.body = body;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public MailRecord setAddress(String address) {
        this.address = address;
        return this;
    }
}
