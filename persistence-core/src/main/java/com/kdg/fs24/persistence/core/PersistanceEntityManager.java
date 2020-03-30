/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.persistence.core;

import com.kdg.fs24.application.core.service.funcs.CustomCollectionImpl;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
//import com.kdg.fs24.services.api.Service;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
//import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.persistence.api.PersistenceAction;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.persistence.EntityTransaction;
import com.kdg.fs24.spring.core.bean.AbstractApplicationBean;
import java.util.Collection;
import com.kdg.fs24.persistence.api.QueryExecutor;
import com.kdg.fs24.persistence.api.PersistenceQuery;
import javax.persistence.Query;

/**
 *
 * @author N76VB
 */
public class PersistanceEntityManager extends AbstractApplicationBean {

    private final AtomicBoolean safeMode = NullSafe.createObject(AtomicBoolean.class);
    private volatile EntityManager entityManager;
    private volatile EntityManagerFactory factory;
    private volatile String persistenceUnitName;
    //private static SessionFactory sessionFactory;
    private volatile Map<String, Object> properties;
    //private static final QueryExecutor QUERY_EXECUTOR_NULL = null;

    public PersistanceEntityManager() {

    }

    public PersistanceEntityManager(final String persistenceUnitName) {

        NullSafe.create(persistenceUnitName)
                .execute(() -> {
//sessionFactory = new Configuration().configure().buildSessionFactory();
                    LogService.LogInfo(this.getClass(), () -> String.format("Try 2 create persistence '%s'",
                            persistenceUnitName));

                    this.factory = Persistence.createEntityManagerFactory(persistenceUnitName);

                    LogService.LogInfo(this.getClass(), () -> String.format("Persistence '%s' is created",
                            persistenceUnitName));

                    createOrUpdateEntityManager();

                })
                .throwException();

//LogService.LogInfo(this.getClass(), () -> String.format("Service '%s' is initialized", this.getClass()));
//persistenceUnitName, properties)
    }

    private void createOrUpdateEntityManager() {

        //synchronized (this) {
        //if (!this.safeMode.get()) {
        if (this.safeMode.compareAndSet(SysConst.BOOLEAN_FALSE, SysConst.BOOLEAN_TRUE)) {

            NullSafe.create()
                    .execute(() -> {

                        LogService.LogInfo(this.getClass(), ()
                                -> String.format("%s: try to create/recreate entity manager ",
                                        this.persistenceUnitName));

                        if (NullSafe.notNull(this.getEntityManager())) {

                            if (this.getEntityManager().isOpen()) {
                                this.getEntityManager().flush();
                                this.getEntityManager().clear();
                                this.getEntityManager().close();
                            }
                        }

                        if (NullSafe.notNull(this.properties)) {
                            this.entityManager = factory.createEntityManager(properties);
                        } else {
                            this.entityManager = factory.createEntityManager();
                        }

                        LogService.LogInfo(this.getClass(), () -> String.format("%s: Successfully create entity manager (%s) ",
                                this.persistenceUnitName,
                                this.getEntityManager().getClass().getCanonicalName()));

                        LogService.LogInfo(this.getClass(), () -> String.format("EMF Properties \n %s ",
                                this.getEmfProperties()));

                    });

            this.safeMode.set(SysConst.BOOLEAN_FALSE);
        }
        //}
    }

    //==========================================================================
    private void closeAll() {
        if (NullSafe.notNull(this.getEntityManager())) {
            this.getEntityManager().clear();
            this.getEntityManager().close();
        }
        if (NullSafe.notNull(this.factory)) {
            if (this.factory.isOpen()) {
                this.factory.close();
            }
        }
    }

    //==========================================================================
    protected void internalInitializeService(final Map<String, Object> properties) {

        NullSafe.create(persistenceUnitName)
                .execute(() -> {
//sessionFactory = new Configuration().configure().buildSessionFactory();
                    this.properties = properties;
                    this.factory = Persistence.createEntityManagerFactory(persistenceUnitName);
                    createOrUpdateEntityManager();
                });
    }

//    @Override
//
//    public void stopService() {
//        NullSafe.create(getEntityManager())
//                .safeExecute((ns_factory) -> {
//                    this.closeAll();
//                });
//        Service.super.stopService();
//    }
    //==========================================================================
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    public void setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    //==========================================================================
    public final void executePersistAction(final PersistenceAction persistAction) {

        NullSafe.create()
                .execute(() -> {
                    persistAction.execute(this.getEntityManager());
                })
                .throwException();
    }

    //==========================================================================
    public final void executeTransaction(final PersistenceAction persistAction) {

        NullSafe.create()
                .execute(() -> {

                    final EntityTransaction entityTransaction = this.getEntityManager().getTransaction();

                    NullSafe.create()
                            .execute(() -> {

//                        LogService.LogInfo(this.getClass(), () -> String.format("Start jpa transaction (%b,%d)",
//                                this.getEntityManager().getTransaction().isActive(),
//                                this.getEntityManager().getTransaction().hashCode()));
                                entityTransaction.begin();
                                //}
                                this.executePersistAction(persistAction);

                                entityTransaction.commit();

//                        LogService.LogInfo(this.getClass(), () -> String.format("Finish jpa transaction (%d)",
//                                this.getEntityManager().getTransaction().hashCode()));
                            }).catchException((e) -> {

                        //if (this.getEntityManager().getTransaction().isActive()) {
                        LogService.LogInfo(this.getClass(), () -> String.format("Rollback jpa transaction (%s, %d)",
                                NullSafe.getErrorMessage(e), entityTransaction.hashCode()));
                        NullSafe.create(SysConst.STRING_NULL, NullSafe.DONT_THROW_EXCEPTION)
                                .execute(() -> {
                                    entityTransaction.rollback();
                                });

                        //reCreateEntityManager();
                        //}
                    }).throwException();
                }).throwException();
    }

    //==========================================================================
    public final String getEmfProperties() {
        final CustomCollectionImpl customCollection = NullSafe.createObject(CustomCollectionImpl.class, "Emf properties \n");

        this.factory.getProperties()
                .keySet()
                .stream()
                .unordered()
                .forEach(obj -> {

                    customCollection.addCustomRecord(() -> String.format("%40s = '%s'\n",
                            obj,
                            //ServiceFuncs.getStringObjValue(paramsMap.get(obj)), NullSafe.create(paramsMap.get(obj))
                            ServiceFuncs.getStringObjValue(this.factory.getProperties().get(obj))));
                });
        return customCollection.getRecord();
    }
    //==========================================================================
//    public final <T> Collection<T> executeNativeQuery(final String sql) {
//        return this.executeNativeQuery(sql, SysConst.STRING_NULL);
//    }

    //==========================================================================
    public final <T> Collection<T> executeNativeQuery(final String sql, final Class<? extends PersistenceQuery> clazz) {
        return this.executeNativeQuery(sql, clazz, null);
    }

    //==========================================================================
    public final <T> Collection<T> executeNativeQuery(final String sql,
            final Class<? extends PersistenceQuery> clazz,
            final QueryExecutor queryExecutor) {

        return NullSafe.create()
                .execute2result(() -> {

                    //final Collection<T> result; // = ServiceFuncs.<T>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
                    final Query query = this.getEntityManager()
                            .createNativeQuery(sql, clazz.getSimpleName());

                    if (NullSafe.notNull(queryExecutor)) {
                        queryExecutor.execute(query);
                    }

                    return query.getResultList();

                }).<Collection<T>>getObject();
    }
}
