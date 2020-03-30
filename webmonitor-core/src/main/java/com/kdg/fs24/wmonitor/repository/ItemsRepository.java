/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.repository;

import com.kdg.fs24.spring.core.api.ApplicationRepository;
import com.kdg.fs24.spring.core.api.ApplicationJpaRepository;
import com.kdg.fs24.wmonitor.entity.HibernateItem;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author N76VB
 */
//@Repository
public interface ItemsRepository extends ApplicationJpaRepository<HibernateItem, Integer> {

//    HibernateItem findById(Integer id);
    Collection<HibernateItem> findByLinkHeader(String link_header3);

    @Query("select w from HibernateItem w where lower(w.linkHeader) like %:keyword%")
    //@Query(value = "select w.* from w_items w where lower(w.link_header) like %:keyword%", nativeQuery = true)
    Collection<HibernateItem> findByKeyWord(@Param("keyword") String keyword);

}
