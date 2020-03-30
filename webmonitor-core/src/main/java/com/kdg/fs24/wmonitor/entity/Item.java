/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 *
 * @author N76VB
 */
@MappedSuperclass
public class Item implements Serializable {

    public Item() {

    }
//    private Integer id;
    private Integer target_id;
    private LocalDateTime date_found;
    private String item_url;
    @Column(name = "link_header")
    private String linkHeader;
    private Integer url_hc;
    private String emails;
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;

    public Integer getTarget_id() {
        return target_id;
    }

    public void setTarget_id(Integer target_id) {
        this.target_id = target_id;
    }

    public LocalDateTime getDate_found() {
        return date_found;
    }

    public void setDate_found(LocalDateTime date_found) {
        this.date_found = date_found;
    }

    public String getItem_url() {
        return item_url;
    }

    public void setItem_url(String item_url) {
        this.item_url = item_url;
    }

    public String getLinkHeader() {
        return linkHeader;
    }

    public void setLinkHeader(String linkHeader) {
        //this.link_header = link_header.substring(0, Math.min(link_header.length(), 100));
        this.linkHeader = linkHeader;
    }

    public Integer getUrl_hc() {
        return url_hc;
    }

    public void setUrl_hc(Integer url_hc) {
        this.url_hc = url_hc;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public LocalDateTime getCreated() {
        return this.created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
}
