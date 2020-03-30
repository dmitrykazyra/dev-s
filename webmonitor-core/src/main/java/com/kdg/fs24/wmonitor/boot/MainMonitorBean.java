/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.wmonitor.boot;

import com.kdg.fs24.wmonitor.query.KeyWordRecords;
import org.springframework.core.env.Environment;
//import com.kdg.fs24.registry.api.ApplicationSetup;
//import com.kdg.fs24.services.api.Service;
//import com.kdg.fs24.services.api.ServiceLocator;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Collection;
import com.kdg.fs24.wmonitor.entity.AbstractSpyEntity;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.kdg.fs24.application.core.sysconst.SysConst;
import org.springframework.beans.factory.annotation.Autowired;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import org.springframework.stereotype.Component;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.service.funcs.CustomCollectionImpl;
import com.kdg.fs24.wmonitor.entity.HibernateItem;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.hibernate.Session;
import com.kdg.fs24.spring.core.mail.MailManager;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import com.kdg.fs24.spring.core.bean.AbstractApplicationBean;

@Component
public class MainMonitorBean extends AbstractApplicationBean {

//==========================================================================
    private volatile Collection<AbstractSpyEntity> jobsList; // ServiceFuncs.<AbstractSpyEntity>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
    private volatile Boolean inProgess = Boolean.FALSE;
    //==========================================================================
    // мониторинг состояния БД через отправку почтой     
    private TimerTask monitorTask = null;
    private Timer monitorTaskTimer = null;
    
    @Value("${refresh.interval:30}")
    private int refreshInterval = 30;
    
    @Value("${web.user.agent:Mozilla}")
    private String userAgentSetup;

    @Value("${web.delay.between:1000}")
    private int reqDelay;

    @Value("${web.request.timeout:1000}")
    private int reqTimeOut;

    //
    @Autowired
    private Environment env;

    @Autowired(required = false)
    private PersistanceEntityManager persistanceEntityManager;

    @Autowired(required = false)
    private MailManager mailManager;

    public MainMonitorBean() {

    }

    //==========================================================================
    @PostConstruct
    public void postConstruct() {

        final int monitorDbState = this.refreshInterval;

        LogService.LogInfo(this.getClass(), () -> String.format("refresh.interval: %d mins",
                monitorDbState));

        if (monitorDbState > 0) {

            this.monitorTask = new TimerTask() {
                @Override
                public void run() {
                    MainMonitorBean.this.getBonuses();
                }
            };

            this.monitorTaskTimer = NullSafe.createObject(Timer.class);
            this.monitorTaskTimer.schedule(this.monitorTask, 1000, monitorDbState * 60000);
        }
    }

    //==========================================================================
    public void getBonuses() {
        //LogService.LogInfo(this.getClass(), String.format("update db state"));

        if ((this.inProgess) || (NullSafe.isNull(this.persistanceEntityManager))) {
            return;
        }

        synchronized (this) {

            final String srvHeader = this.getClass().getCanonicalName();
            final String srvMsg = srvHeader;
            final String classPackage = "com.kdg.fs24.wmonitor.entity.";

            final String srvHtml = "<html><body><p>"
                    .concat(srvMsg)
                    .replace("\n", "<br/>")
                    .concat("</p></body></html>");

            NullSafe.create(this.jobsList)
                    .whenIsNull(() -> {
                        this.jobsList = ServiceFuncs.<AbstractSpyEntity>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
                    })
                    .execute(() -> {

                        this.inProgess = SysConst.BOOLEAN_TRUE;

                        this.jobsList.clear();

                        final Collection<KeyWordRecords> items = this.persistanceEntityManager
                                .<KeyWordRecords>executeNativeQuery(
                                        "SELECT  t.id, t.alg_id, t.target_code,t.target_url, t.refresh_period, t.email, t.page_add, kw.kw_record keyword, (t.id || kw.kw_record) super_id"
                                        + "  FROM w_targets t, w_keywords kw"
                                        + "  WHERE t.is_actual ORDER BY t.target_code",
                                        KeyWordRecords.class);

                        items
                                .stream()
                                .forEach((record) -> {

                                    final String className = record.getTarget_code();
                                    final Class<AbstractSpyEntity> clazz = NullSafe.create()
                                            .execute2result(() -> (Class<AbstractSpyEntity>) Class.forName(classPackage.concat(className)))
                                            .<Class<AbstractSpyEntity>>getObject();

                                    final AbstractSpyEntity item = NullSafe.createObject(clazz);
                                    //qry.fillRecord(item);

                                    item.setId(record.getId());
                                    item.setAlg_id(record.getAlg_id());
                                    item.setEmail(record.getEmail());
                                    item.setKeyword(record.getKeyword());
                                    item.setPage_add(record.getPage_add());
                                    item.setRefresh_period(record.getRefresh_period());
                                    item.setTarget_code(record.getTarget_code());
                                    item.setTarget_url(record.getTarget_url());

                                    this.jobsList.add(item);
                                });

                        LogService.LogInfo(this.getClass(), () -> String.format("spring.application.name (%s)", env.getProperty("spring.application.name")));

                        LogService.LogInfo(this.getClass(), () -> String.format("Scanner is started '%s' (%d keywords)",
                                srvHeader,
                                this.jobsList.size()));
                        LogService.LogDebug(this.getClass(), () -> "warn+++");

                        //final String userAgentSetup = "Mozilla";
                        //final int reqDelay = 1000;
                        //final int reqTimeOut = 30000;
                        this.jobsList
                                .stream()
                                .forEach((engine) -> {

                                    final String url = engine
                                            .getTarget_url()
                                            .replace("[KW]", engine.getKeyword())
                                            .replace(" ", "+");

                                    final NullSafe ns = NullSafe.create(url)
                                            .initWatcher()
                                            .execute(() -> {

//                                            LogService.LogInfo(engine.getClass(), () -> String.format("Proxy: %s:%s ",
//                                                    System.getProperty("http.proxyHost"),
//                                                    System.getProperty("http.proxyPort")));
                                                Thread.sleep(this.reqDelay);

                                                final Document doc = Jsoup.connect(url.toLowerCase())
                                                        //                                                    .data("query", "Java")
                                                        .userAgent(this.userAgentSetup)
                                                        //                                                    .cookie("auth", "token")
                                                        .validateTLSCertificates(false)
                                                        .timeout(this.reqTimeOut)
                                                        .get();

                                                // парсим всю страницу
                                                engine.initialize(persistanceEntityManager);
                                                // парсим всю страницу
                                                this.createHibernateItems(
                                                        engine.createDocuments(doc, engine.getKeyword()),
                                                        engine.getKeyword(),
                                                        engine.getTarget_code()
                                                );

                                            }).catchException(e -> {
                                        LogService.LogErr(engine.getClass(), () -> String.format("Error Acessing page '%s' \n '%s'",
                                                url,
                                                NullSafe.getErrorMessage(e)));
                                    });

//                                    if (TestConst.TEST_MODE_RUNNING) {
//                                        ns.throwException();
//                                    }
                                });
                        LogService.LogInfo(this.getClass(), () -> String.format("Scanner is stopped '%s'", srvHeader));

                    })
                    .
                    finallyBlock(() -> {
                        this.inProgess = Boolean.FALSE;
                    })
                    .throwException();
        }
    }

    //==========================================================================
    @Transactional
    protected void createHibernateItems(
            final Collection<HibernateItem> collection,
            final String itemName,
            final String targetCode) {
        if (collection.isEmpty()) {
            return;
        }

        LogService.LogInfo(this.getClass(), () -> String.format("%s: %d new [%s]",
                targetCode,
                collection.size(),
                itemName));

        final CustomCollectionImpl customCollection = NullSafe.createObject(CustomCollectionImpl.class, targetCode);

        // список ссылок для письма
        NullSafe.runNewThread(() -> {
            collection
                    .stream()
                    .unordered()
                    .forEach((item) -> {
                        customCollection.addCustomRecord(() -> String.format("<tr><td>%s </td><td> <a href=%s>%s</a></td></tr>",
                                item.getLinkHeader(),
                                item.getItem_url(),
                                item.getItem_url()));
                    });
        });

        persistanceEntityManager
                .executeTransaction((entityManager) -> {

                    entityManager
                            .unwrap(Session.class)
                            .setJdbcBatchSize(Math.min(100, collection.size()));
                    collection
                            .stream()
                            .unordered()
                            .forEach((item) -> {

                                item.setCreated(LocalDateTime.now());
                                entityManager.persist(item);

                            });
                });

        final String regKey = String.format("wmonitor.entity.%s.email", this.getClass().getSimpleName()).toLowerCase();

        final String emailaddr = env.getProperty(regKey, SysConst.EMPTY_STRING);

        mailManager.send(String.format("%s: %s [%d]",
                targetCode,
                itemName,
                collection.size()), "<table>"
                .concat(customCollection.getRecord())
                .concat("</table>"), !emailaddr.isEmpty() ? emailaddr : SysConst.STRING_NULL);
    }
}
