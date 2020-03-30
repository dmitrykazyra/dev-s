/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.entity;

import com.kdg.fs24.wmonitor.api.Alg_id;
import com.kdg.fs24.wmonitor.api.Email;
import com.kdg.fs24.wmonitor.api.Page_add;
import com.kdg.fs24.wmonitor.api.Refresh_period;
import com.kdg.fs24.wmonitor.api.Target_code;
import com.kdg.fs24.wmonitor.api.Target_id;
import com.kdg.fs24.wmonitor.api.Target_url;
import org.jsoup.nodes.Document;
import java.util.Collection;

/**
 *
 * @author kazyra_d
 */
@Target_id(id = 500)
@Alg_id(id = 100)
@Target_code(code = "Necropol")
@Target_url(url = "http://m-necropol.ru/")
@Refresh_period(period = 30000)
@Email(mailto = "kozyro@mail.ru")
@Page_add(page_size = 25)
public final class Necropol extends AbstractSpyEntity {

    public Collection<HibernateItem> createDocuments(final Document document, final String keyWords) {
        return null;
    }
}
