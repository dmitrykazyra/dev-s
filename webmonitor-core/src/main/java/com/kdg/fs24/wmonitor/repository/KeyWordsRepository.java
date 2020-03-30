/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.repository;

import com.kdg.fs24.spring.core.api.ApplicationJpaRepository;
import com.kdg.fs24.wmonitor.query.KeyWordRecords;
import java.util.Collection;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author N76VB
 */
public interface KeyWordsRepository extends ApplicationJpaRepository<KeyWordRecords, String> {

    @Query(value = "SELECT  t.id, t.alg_id, t.target_code,t.target_url, t.refresh_period, t.email, t.page_add, kw.kw_record keyword, (t.id || kw.kw_record) super_id"
            + "  FROM w_targets t, w_keywords kw"
            + "  WHERE t.is_actual ORDER BY t.target_code", nativeQuery = true)
    Collection<KeyWordRecords> findKeyWords();
}
