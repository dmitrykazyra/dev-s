/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@MappedSuperclass
@Data
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

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
}
