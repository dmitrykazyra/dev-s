/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.query;

import com.kdg.fs24.wmonitor.entity.AbstractSpyEntity;
import com.kdg.fs24.wmonitor.entity.Item;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import org.jsoup.nodes.Document;
import com.kdg.fs24.persistence.api.PersistenceQuery;
import java.util.Collection;
import com.kdg.fs24.wmonitor.entity.HibernateItem;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Objects;

/**
 *
 * @author N76VB
 */
@SqlResultSetMapping(name = "KeyWordRecords",
        entities = @EntityResult(
                entityClass = KeyWordRecords.class,
                fields = {
                    @FieldResult(name = "id", column = "id")
                    ,
                    @FieldResult(name = "super_id", column = "super_id")
                    ,
                             @FieldResult(name = "target_code", column = "target_code")
                    ,
                             @FieldResult(name = "alg_id", column = "alg_id")
                    ,
                             @FieldResult(name = "target_url", column = "target_url")
                    ,
                             @FieldResult(name = "refresh_period", column = "refresh_period")
                    ,
                             @FieldResult(name = "email", column = "email")
                    ,
                             @FieldResult(name = "page_add", column = "page_add")
                    ,
                             @FieldResult(name = "keyword", column = "keyword")
                }))
@Entity
//    @Getter
//    @Setter
public class KeyWordRecords extends AbstractSpyEntity<Item> implements PersistenceQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String super_id;

    public KeyWordRecords() {
        super();
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(this.getId1(), this.getKeyword());
//    }

//    @Id
//    @Override
//    public String getKeyword() {
//        return keyword;
//    }
//    @Override
//    public String queryName() {
//        return this.getClass().getSimpleName();
//    }
    @Override
    public Collection<HibernateItem> createDocuments(final Document document, final String keyWords) {
        return null;
    }

    public String getSuper_id() {
        return super_id;
    }

    public void setSuper_id(String super_id) {
        this.super_id = super_id;
    }

}
