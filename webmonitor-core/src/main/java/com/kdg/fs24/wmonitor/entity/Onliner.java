/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.entity;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.wmonitor.api.*;
import java.util.Collection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author kazyra_d
 */
@Target_id(id = 400)
@Alg_id(id = 100)
@Target_code(code = "Onliner")
@Target_url(url = "https://baraholka.onliner.by/search.php?q=[KW]&by=created")
@Refresh_period(period = 30000)
@Email(mailto = "kozyro@mail.ru")
@Page_add(page_size = 25)
public class Onliner extends AbstractSpyEntity<HibernateItem> {

    private String beanProperty;

    @Override
    public Collection<HibernateItem> createDocuments(final Document document, final String keyWords) {

        final Collection<HibernateItem> collection = ServiceFuncs.<HibernateItem>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

        // разбираем документ
        final Element item = document.getElementsByClass("ba-tbl-list fleamarket__1").first();
        //final Elements items = item.getElementsByClass("frst ph colspan");
        final Elements items = item.getElementsByTag("tr");

        //final Elements items = document.getAllElements();
        //final String html_items = items.html();
        items
                .stream()
                .unordered()
                //                .filter(itemf -> !itemf.html().contains("th-sort-tabs"))
                //                .filter(itemf -> !itemf.html().contains("a-c selected"))
                .filter(itemf -> itemf.html().contains("frst ph colspan"))
                .forEach(item1 -> {

                    //final String val = item1.html();
                    NullSafe.create(item1.baseUri())
                            .execute(() -> {

                                final String html = item1.html();

                                final Elements elUrls = item1.getElementsByAttribute("href");

                                final String url = "https://baraholka.onliner.by".concat(elUrls.first().attributes().get("href"));

                                final String price = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                        .execute2result(() -> {
                                            return item1.getElementsByClass("price-primary").first().text();
                                        }).<String>getObject();

                                final String created = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                        .execute2result(() -> {
                                            return item1.getElementsByClass("ba-post-up").first().text();
                                        }).<String>getObject();

                                final String title = price
                                        .concat(", ")
                                        .concat(NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                                .execute2result(() -> {
                                                    return item1.getElementsByClass("wraptxt").first().text();
                                                }).<String>getObject())
                                        .concat(", ")
                                        .concat(created);

                                //final String html = item1.html();
                                // добавляем только новые итимы
                                this.registerItem(collection, url, title);
                            });

                });

        //this.createItems(collection, keyWords);
        return collection;

    }

    public String getBeanProperty() {
        return beanProperty;
    }

    public void setBeanProperty(String beanProperty) {
        this.beanProperty = beanProperty;
    }
}
