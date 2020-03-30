/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.entity;

import com.kdg.fs24.application.core.service.funcs.CustomCollectionImpl;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.core.api.ActionRecord;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.fields.desc.AppFieldsCaptions;
import com.kdg.fs24.fields.desc.FieldDescription;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.references.api.AbstractRefRecord;
import com.kdg.fs24.references.api.ReferenceRec;
//import com.kdg.fs24.services.api.ServiceLocator;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import com.kdg.fs24.wmonitor.query.ExistsItem;
import java.time.LocalDateTime;
import java.util.Map;
import org.jsoup.nodes.Document;
import java.util.Collection;
import javax.transaction.Transactional;
import javax.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import org.hibernate.Session;

/**
 *
 * @author N76VB
 */
@MappedSuperclass
public abstract class AbstractSpyEntity<T extends Item> extends AbstractRefRecord
        implements ReferenceRec, PersistenceEntity {

    private Integer id;
    private String target_code;
    private Integer alg_id;
    @Transient
    private Boolean is_actual;
    private String target_url;
    private Integer refresh_period;
    private String email;
    private String keyword;
    private Integer page_add;

    public AbstractSpyEntity() {

    }

    @Transient
    private final Map<Integer, String> EXISTS_LINKS = ServiceFuncs.<Integer, String>getOrCreateMap(ServiceFuncs.MAP_NULL);

    public Integer getId() {
        return id;
    }

    public AbstractSpyEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTarget_code() {
        return target_code;
    }

    public AbstractSpyEntity setTarget_code(String target_code) {
        this.target_code = target_code;
        return this;
    }

    public Integer getAlg_id() {
        return alg_id;
    }

    public AbstractSpyEntity setAlg_id(Integer alg_id) {
        this.alg_id = alg_id;
        return this;
    }

    public Boolean getIs_actual() {
        return is_actual;
    }

    public AbstractSpyEntity setIs_actual(Boolean is_actual) {
        this.is_actual = is_actual;
        return this;
    }

    public String getTarget_url() {
        return target_url;
    }

    public AbstractSpyEntity setTarget_url(String target_url) {
        this.target_url = target_url;
        return this;
    }

    public Integer getRefresh_period() {
        return refresh_period;
    }

    public AbstractSpyEntity setRefresh_period(Integer refresh_period) {
        this.refresh_period = refresh_period;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AbstractSpyEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getKeyword() {
        return keyword;
    }

    public AbstractSpyEntity setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public Integer getPage_add() {
        return page_add;
    }

    public AbstractSpyEntity setPage_add(Integer page_add) {
        this.page_add = page_add;
        return this;
    }

    //==========================================================================
    public void initialize(final PersistanceEntityManager persistanceEntityManager) {

        if (EXISTS_LINKS.isEmpty()) {
            final Collection<ExistsItem> items = persistanceEntityManager
                    .<ExistsItem>executeNativeQuery(
                            "select url_hc, item_url from w_items"
                            + "  where target_id=:ID"
                            + "  and date_found>=current_date - 5",
                            ExistsItem.class, (query) -> {
                                query.setParameter("ID", this.getId());
                            });

            items
                    .stream()
                    .forEach(item -> {
                        final Integer url_hc = item.getUrl_hc();
                        final String item_url = item.getItem_url();

                        this.EXISTS_LINKS.put(url_hc, item_url);
                    });
        }
    }
    //==========================================================================

    public abstract Collection<HibernateItem> createDocuments(final Document document, final String keyWords);

    @Deprecated
    protected final void createItems(final Collection<T> collection, final String itemName) {

//        if (collection.isEmpty()) {
//            return;
//        }
//
//        LogService.LogInfo(this.getClass(), () -> String.format("%s: %d new [%s]",
//                this.getTarget_code(),
//                collection.size(),
//                itemName));
//
//        final CustomCollectionImpl customCollection = NullSafe.createObject(CustomCollectionImpl.class,
//                this.getTarget_code());
//
//        ServiceLocator
//                .find(AbstractJdbcService.class)
//                .createCallBath("{call monitor_insertorupdate_item(:t_id, :df, :iu, :lh, :uh, :em)}")
//                .execBatch(stmt -> {
//
//                    collection
//                            .stream()
//                            .unordered()
//                            .forEach((item) -> {
//
//                                NullSafe.create()
//                                        .execute(() -> {
//                                            stmt.setParamByName("t_id", this.getId());
//                                            stmt.setParamByName("df", item.getDate_found());
//                                            stmt.setParamByName("iu", item.getItem_url());
//                                            stmt.setParamByName("lh", item.getLink_header());
//                                            stmt.setParamByName("uh", item.getUrl_hc());
//                                            stmt.setParamByName("em", item.getEmails());
//                                            stmt.addBatch();
//                                        });
//
//                                //customCollection.addCustomRecord(() -> String.format("%s: <a href=%s>%s</a><br>",
//                                customCollection.addCustomRecord(() -> String.format("<tr><td>%s </td><td> <a href=%s>%s</a></td></tr>",
//                                        item.getLink_header(),
//                                        item.getItem_url(),
//                                        item.getItem_url()));
//
//                            });
//                }).commit();
//
//        final String regKey = String.format("wmonitor.entity.%s.email", this.getClass().getSimpleName()).toLowerCase();
//        final String emailaddr = ServiceLocator
//                .find(ApplicationSetup.class)
//                .getRegParam(regKey, SysConst.EMPTY_STRING);
//
//        LogService.log4mail(this.getClass(), String.format("%s: %s [%d]",
//                this.getTarget_code(),
//                itemName,
//                collection.size()),
//                "<table>"
//                        //.concat("<br><br>")
//                        .concat(customCollection.getRecord())
//                        .concat("</table>"), !emailaddr.isEmpty() ? emailaddr : SysConst.STRING_NULL);
    }

    //==========================================================================
    protected void registerItem(final Collection<T> collection,
            final String url,
            final String title) {

        final Integer hc = url.hashCode();

        synchronized (this.EXISTS_LINKS) {

            NullSafe.create(
                    ServiceFuncs.
                            <Integer, String>getMapValue_silent(this.EXISTS_LINKS,
                                    p -> p.getKey().equals(hc)))
                    .whenIsNull(() -> {

                        this.EXISTS_LINKS.put(hc, title);

                        final T newItem = (T) NullSafe.createObject(HibernateItem.class);
                        newItem.setDate_found(LocalDateTime.now());
                        newItem.setItem_url(url);
                        newItem.setLinkHeader(title.substring(0, Math.min(title.length(), 100)));
                        newItem.setUrl_hc(hc);
                        newItem.setTarget_id(this.getId());
                        newItem.setCreated(LocalDateTime.now());

                        collection.add(newItem);
                    });
        }
    }

    //==========================================================================
    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getId(), this.getTarget_code()), this.getId());
    }

}
