/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.entity;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.wmonitor.api.*;
import java.util.Collection;
import org.jsoup.nodes.Document;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author N76VB
 */
@Target_id(id = 300)
@Alg_id(id = 100)
@Target_code(code = "Kufar")
//@Target_url(url = "https://www.kufar.by/listings?query=[KW]&cat=4050")
@Target_url(url = "https://www.kufar.by/listings?query=[KW]&ot=1")
@Refresh_period(period = 30000)
@Email(mailto = "kozyro@mail.ru")
@Page_add(page_size = 25)
//@Deprecated
public class Kufar extends AbstractSpyEntity {

    @Override
    public Collection<HibernateItem> createDocuments(final Document document, final String keyWords) {

        final Collection<HibernateItem> collection = ServiceFuncs.<Item>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

        // разбираем документ
        final Element item = document.getElementById("application");
        final Elements items = item.getElementsByClass("kf-To-7c49d");
        
        final String html = item.html();
        
        //final Elements items = document.getAllElements();
        items
                .stream()
                .unordered()
                .forEach(item1 -> {

                    //final String val = item1.html();
                    NullSafe.create(item1.baseUri())
                            .execute(() -> {
                                final Elements elUrls = item1.getElementsByAttribute("href");

                                final String url = elUrls.first().attributes().get("href");

                                final String price = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                        .execute2result(() -> {
                                            return item1.getElementsByClass("kf-yk-70838").first().text();
                                        }).<String>getObject();

                                final String created = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                        .execute2result(() -> {
                                            return item1.getElementsByClass("kf-ME-4598f").first().text();
                                        }).<String>getObject();

                                final Elements elNames = elUrls.first().getElementsByTag("h3");

                                final String title = price
                                        .concat(", ")
                                        .concat(NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                                .execute2result(() -> {
                                                    return elNames.first().attributes().get("title");
                                                }).<String>getObject())
                                        .concat(", ")
                                        .concat(created);

                                //final String html = item1.html();

                                // добавляем только новые итимы
                                this.registerItem(collection, url, title);
                            });

                });

        this.createItems(collection, keyWords);
        
        return collection;

    }
}
