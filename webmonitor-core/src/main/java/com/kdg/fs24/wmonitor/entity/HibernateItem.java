/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.entity;

import javax.persistence.*;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author N76VB
 */
@Entity
@Table(name = "w_items")
public class HibernateItem extends Item {

    public HibernateItem() {

    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "seq_items", allocationSize = 1)
    private Integer id;

//    @Override
//    public Integer getTarget_id() {
//        return super.getTarget_id();
//    }
//
//    @Override
//    public void setTarget_id(Integer target_id) {
//        super.setTarget_id(target_id);
//    }
//
//    @Override
//    public LocalDateTime getDate_found() {
//        return super.getDate_found();
//    }
//
//    @Override
//    public void setDate_found(LocalDateTime date_found) {
//        super.setDate_found(date_found);
//    }
//
//    @Override
//    public String getItem_url() {
//        return super.getItem_url();
//    }
//
//    @Override
//    public void setItem_url(String item_url) {
//        super.setItem_url(item_url);
//    }
//
//    @Override
//    public String getLink_header() {
//        return super.getLink_header();
//    }
//
//    @Override
//    public void setLink_header(String link_header) {
//        super.setLink_header(link_header);
//    }
//
//    @Override
//    public Integer getUrl_hc() {
//        return super.getUrl_hc();
//    }
//    @Override
//    public void setUrl_hc(Integer url_hc) {
//        super.setUrl_hc(url_hc);
//    }
//
//    @Override
//    public String getEmails() {
//        return super.getEmails();
//    }
//
//    @Override
//    public void setEmails(String emails) {
//        super.setEmails(emails);
//    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
