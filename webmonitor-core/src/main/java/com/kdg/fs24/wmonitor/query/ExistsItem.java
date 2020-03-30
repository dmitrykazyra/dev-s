/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.query;

import com.kdg.fs24.persistence.api.PersistenceQuery;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author N76VB
 */
@SqlResultSetMapping(name = "ExistsItem",
        entities = @EntityResult(
                entityClass = ExistsItem.class,
                fields = {
                    @FieldResult(name = "url_hc", column = "url_hc")
                    ,@FieldResult(name = "item_url", column = "item_url")
                }))
@Entity
public class ExistsItem implements PersistenceQuery {

//    public final static String getQueryName() {
//        return ExistsItem.class.getCanonicalName();
//    }
    @Id
    private Integer url_hc;
    private String item_url;

    public ExistsItem() {

    }

//    @Override
//    public String queryName() {
//        return this.getClass().getSimpleName();
//    }

    public Integer getUrl_hc() {
        return url_hc;
    }

    public void setUrl_hc(Integer url_hc) {
        this.url_hc = url_hc;
    }

    public String getItem_url() {
        return item_url;
    }

    public void setItem_url(String item_url) {
        this.item_url = item_url;
    }

}
