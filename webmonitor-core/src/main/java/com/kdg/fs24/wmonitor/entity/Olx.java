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
@Target_id(id = 1100)
@Alg_id(id = 1100)
@Target_code(code = "Olx")
@Target_url(url = "https://www.olx.ua/list/q-[KW]")
@Refresh_period(period = 30000)
@Email(mailto = "kozyro@mail.ru")
@Page_add(page_size = 25)
public class Olx extends AbstractSpyEntity {

    @Override
    public Collection<HibernateItem> createDocuments(final Document document, final String keyWords) {

        final Collection<HibernateItem> collection = ServiceFuncs.<HibernateItem>createCollection();

        // разбираем документ
        final Elements items = document.getElementsByClass("offer-wrapper");
        //final Elements items = item.getElementsByClass("frst ph colspan");
        //final Elements items = item.getElementsByTag("table");

        //final Elements items = document.getAllElements();
        //final String html_items = items.html();
        items
                .stream()
                .unordered()
                //                .filter(itemf -> !itemf.html().contains("th-sort-tabs"))
                //                .filter(itemf -> !itemf.html().contains("a-c selected"))
                //.filter(itemf -> itemf.html().contains("frst ph colspan"))
                .forEach(item1 -> {

                    //final String val = item1.html();
                    NullSafe.create(item1.baseUri())
                            .execute(() -> {

                                //final String html = item1.html();
                                final Elements elUrls = item1.getElementsByAttribute("href");

                                final String url = (elUrls.first().attributes().get("href"));

                                final String price = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                        .execute2result(() -> {
                                            return item1.getElementsByClass("price").first().text();
                                        }).<String>getObject();

                                final String created = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                        .execute2result(() -> {
                                            final Element e_cr = item1.getElementsByClass("bottom-cell").first();
                                            return e_cr.getElementsByClass("lheight16").first().text();
                                        }).<String>getObject();

                                //final Elements elNames = elUrls.first().getElementsByTag("h3");
                                final String title = NullSafe.create(SysConst.NOT_DEFINED, SysConst.IS_SILENT_EXECUTE)
                                        .execute2result(() -> {
                                            return item1.getElementsByClass("lheight22").first().text();
                                        }).<String>getObject()
                                        .concat(", ")
                                        .concat(price)
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
}
