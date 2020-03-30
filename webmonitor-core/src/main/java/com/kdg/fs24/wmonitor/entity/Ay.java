/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.entity;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.wmonitor.api.Alg_id;
import com.kdg.fs24.wmonitor.api.Email;
import com.kdg.fs24.wmonitor.api.Page_add;
import com.kdg.fs24.wmonitor.api.Refresh_period;
import com.kdg.fs24.wmonitor.api.Target_code;
import com.kdg.fs24.wmonitor.api.Target_id;
import com.kdg.fs24.wmonitor.api.Target_url;
import java.util.Collection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author N76VB
 */
@Target_id(id = 600)
@Alg_id(id = 100)
@Target_code(code = "Ay")
@Target_url(url = "http://ay.by/sch/?kwd=[KW]&order=create")
@Refresh_period(period = 30000)
@Email(mailto = "kozyro@mail.ru")
@Page_add(page_size = 25)
public class Ay extends AbstractSpyEntity {

    @Override
    public Collection<HibernateItem> createDocuments(final Document document, final String keyWords) {

        final Collection<HibernateItem> collection = ServiceFuncs.<Item>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

        NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                .execute(() -> {

                    // разбираем документ
                    final Element item = document.getElementById("lots-table");
                    //final Elements items = item.getElementsByClass("frst ph colspan");
                    final Elements items = item.getElementsByTag("li");

                    //final Elements items = document.getAllElements();
                    //       final String html_items = items.html();
                    items
                            .stream()
                            .unordered()
                            //                .filter(itemf -> !itemf.html().contains("th-sort-tabs"))
                            //                .filter(itemf -> !itemf.html().contains("a-c selected"))
                            //                .filter(itemf -> itemf.html().contains("viewer-type-grid__li"))
                            .forEach(item1 -> {

                                //final String val = item1.html();
                                NullSafe.create(item1.baseUri())
                                        .execute(() -> {

                                            final String html = item1.html();
//                                final Elements elUrls = item1.getElementsByAttribute("href");
//
//                                final String url = elUrls.first().attributes().get("href");
                                            final String url = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                                    .execute2result(() -> item1.getElementsByAttribute("href").first().attr("href"))
                                                    .<String>getObject();

                                            final String price = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                                    .execute2result(() -> item1.getElementsByClass("item-type-card__info").first().text())
                                                    .<String>getObject();

                                            final String title = price
                                                    .concat(", ")
                                                    .concat(NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                                            .execute2result(() -> item1.getElementsByClass("item-type-card__title").first().text())
                                                            .<String>getObject());

                                            //final String html = item1.html();
                                            // добавляем только новые итимы
                                            this.registerItem(collection, url, title);
                                        });

                            });
                });

        // this.createItems(collection, keyWords);
        return collection;

    }
}
