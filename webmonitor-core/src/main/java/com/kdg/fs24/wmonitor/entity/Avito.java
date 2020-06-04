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
@Target_id(id = 1000)
@Alg_id(id = 1000)
@Target_code(code = "Avito")
//@Target_url(url = "https://www.kufar.by/listings?query=[KW]&cat=4050")
@Target_url(url = "https://www.avito.ru/rossiya?q=[KW]")
@Refresh_period(period = 30000)
@Email(mailto = "kozyro@mail.ru")
@Page_add(page_size = 25)
public class Avito extends AbstractSpyEntity {

    @Override
    public Collection<HibernateItem> createDocuments(final Document document, final String keyWords) {

        final Collection<HibernateItem> collection = ServiceFuncs.<HibernateItem>createCollection();

        // разбираем документ
        //final Element item = document.getElementById("application");
        //final Elements items = document.getElementsByTag("div");
        final Elements items = document.getElementsByClass("item__line");

        final String htmlitems = items.html();
        //final Elements items = document.getAllElements();
        items
                .stream()
                .unordered()
                //.filter(f-> f.html().contains("http://schema.org/Product"))
                .forEach(item1 -> {

                    //final String val = item1.html();
                    NullSafe.create(item1.baseUri())
                            .execute(() -> {
                                final Elements elUrls = item1.getElementsByAttribute("href");

                                final String html = item1.html();

                                final String url = "https://www.avito.ru".concat(elUrls.first().attributes().get("href"));

                                final String price = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                        .execute2result(() -> item1.getElementsByClass("price").first().text())
                                        .< String>getObject();

                                final String created = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                        .execute2result(() -> {
                                            return item1.getElementsByClass("js-item-date c-2").first().text();
                                        }).<String>getObject();

                                final String title = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                        .execute2result(() -> {
                                            return item1.getElementsByClass("snippet-link").first().text();
                                        }).<String>getObject()
                                        .concat(", ")
                                        .concat(price)
                                        .concat(", ")
                                        .concat(created);

                                //final String html1 = item1.html();
                                // добавляем только новые итимы
                                this.registerItem(collection, url, title);
                            });

                });

        //this.createItems(collection, keyWords);
        return collection;

    }
}
