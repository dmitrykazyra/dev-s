/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.entity;

import com.kdg.fs24.wmonitor.api.*;
import org.jsoup.nodes.Document;
import java.util.Collection;
/**
 *
 * @author kazyra_d
 */
@Target_id(id = 100)
@Alg_id(id = 100)
@Target_code(code = "Google")
@Target_url(url = "https://www.google.com/search?client=firefox-b-d&q=[KW]")
@Refresh_period(period = 30000)
@Email(mailto = "kozyro@mail.ru")
@Page_add(page_size = 25)
public final class Google extends AbstractSpyEntity {
    
    public Collection<HibernateItem> createDocuments(final Document document, final String keyWords) {
        return null;
    }

}
