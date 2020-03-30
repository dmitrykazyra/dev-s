/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.core;

import com.kdg.fs24.application.core.log.LogService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kdg.fs24.entity.collection.EntityBusinessRulesListService;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.entity.core.api.ActionRecord;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.entity.core.api.BusinessRule;
import com.kdg.fs24.entity.core.api.EntityWarningsList;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.kdg.fs24.abs.controller.AbstractController;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.entity.core.api.Action;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.log.LogService;
import java.util.Collection;
import com.kdg.fs24.test.api.TestResult;
import java.util.Map;
import com.kdg.fs24.entity.core.api.Action4Test;
//import com.kdg.fs24.entity.core.attr.AttrsCollection;
import javax.annotation.PostConstruct;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
//import com.kdg.fs24.listeners.api.ListenersCollection;
//import com.kdg.fs24.entity.core.api.EntityEventListener;
//import com.kdg.fs24.listeners.api.Event;
import com.kdg.fs24.persistence.api.PersistenceEntity;
//import com.kdg.fs24.tce.api.StopWatcher;
import com.kdg.fs24.entity.marks.EntityMarkList;

// абстрактная сущность
//@MappedSuperclass
public abstract class AbstractActionEntity<T extends AbstractAction>
        extends AbstractPersistenceEntity
        implements ActionEntity<T> {

    private Map<Class<T>, Action4Test<T>> map4test_afterInitialize;

    private Map<Class<T>, Action4Test<T>> map4test_afterCalculation;

    public Action execEntityAction(
            final Integer action_code_id,
            final Boolean silentExec) throws InternalAppException {
        return this.execEntityAction(action_code_id, silentExec, null, SysConst.SERVICE_USER_ID, SysConst.NOT_DEFINED);
    }

    private EntityStatus entityStatus;

    @Override
    public EntityStatus getEntityStatus() {

        return entityStatus;
    }

    @Override
    public void postConstruct() {
        // РёРЅРёС†РёР°Р»РёР·РёСЂРѕРІР°Р»Рё РєРѕР»Р»РµРєС†РёСЋ Р°С‚СЂРёР±СѓРѕРІ
        //this.getAttrsCollection().initialize(this);
    }

    private TestResult testResult;

    @JsonIgnore
    @Override
    public TestResult getTestResult() {
        return this.testResult;
    }

    public void doInitialTest() {
        final Class<? extends AbstractActionEntity> clazz = this.getClass();
//        NullSafe.create()
//                .execute(() -> {
//                    // проверяем наличие аннотации EntityTypeId
//                    if (NullSafe.isNull(this.getEntityType())) {
//                        throw new EntityInitializeException("EntityType is not initialized");
//                    }
//
//                    // проверяем наличие аннотации defaultEntityStatus
//                    if (NullSafe.isNull(this.getDefaultEntityStatus())) {
//                        throw new EntityInitializeException("defaultEntityStatus is not initialized");
//                    }
//                    // проверяем наличие аннотации 
//                    //this.getActionClassesCollection(clazz);
//                    //
//                    if (NullSafe.isNull(this.getAcc())) {
//                        throw new EntityInitializeException("ActionClassesCollection is not initialized");
//                    }
//                })
//                .catchException(e -> {
//
//                    LogService.LogErr(clazz, () -> String.format("Initial test exception (entityClass='%s'), \n [%s])",
//                            clazz,
//                            NullSafe.getErrorMessage(e)));
//                })
//                .throwException();
    }

    //==========================================================================
    @JsonIgnore
    private Map getTestMap_afterInitialize() {
        return NullSafe.create(this.map4test_afterInitialize)
                .whenIsNull(() -> {
                    this.map4test_afterInitialize = ServiceFuncs.<Class<T>, Action4Test<T>>getOrCreateMap(map4test_afterInitialize);
                    return this.map4test_afterInitialize;
                })
                .<Map>getObject();
    }

    @JsonIgnore
    private Map getTestMap_afterCalculation() {
        return NullSafe.create(this.map4test_afterCalculation)
                .whenIsNull(() -> {
                    this.map4test_afterCalculation = ServiceFuncs.<Class<T>, Action4Test<T>>getOrCreateMap(map4test_afterCalculation);
                    return this.map4test_afterCalculation;
                })
                .<Map>getObject();
    }

    //==========================================================================
    public void resetTestMap() {

        this.getTestMap_afterCalculation().clear();
        this.getTestMap_afterInitialize().clear();
    }

    //==========================================================================
    public <ACT extends AbstractAction> void prepareAction_afterCalculation(final Class<ACT> actClass, final Action4Test<ACT> lop) {

        //getTestMap();
        this.getTestMap_afterCalculation().put((Class<T>) actClass, (Action4Test<T>) lop);
    }

    //==========================================================================
    public <ACT extends AbstractAction> void prepareAction_afterInitialize(final Class<ACT> actClass, final Action4Test<ACT> lop) {

        //getTestMap();
        this.getTestMap_afterInitialize().put((Class<T>) actClass, (Action4Test<T>) lop);
    }

    //==========================================================================
    public void processActionTest_afterCalculation(final T action) {

//        final Map<Class<T>, Action4Test<T>> actList = (((Map<Class<T>, Action4Test<T>>) NullSafeImpl.create(map4test)
//                .whenIsNull(() -> {
//                    this.map4test = ServiceFuncs.<Class<T>, Action4Test<T>>getOrCreateMap(this.map4test);
//                    return this.map4test;
//                }).getObject())
//                .entrySet())
//                .stream()
//                .filter(p -> p.getKey().equals(action.getClass()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        final Action4Test<T> a4t = ServiceFuncs.<Class<T>, Action4Test<T>>getMapValue_silent(map4test_afterCalculation,
                p -> p.getKey().equals(action.getClass()));

        if (null != a4t) {
            a4t.advancedProcessAction((T) action);
        }
//        if (!actList.isEmpty()) {
//            actList
//                    .entrySet()
//                    .stream()
//                    .forEach((testAction) -> {
//                        testAction
//                                .getValue()
//                                .advancedProcessAction((T) action);
//                    });
//        }
    }

    //==========================================================================
    public void processActionTest_afterInitialize(final T action) {

        final Action4Test<T> a4t = ServiceFuncs.<Class<T>, Action4Test<T>>getMapValue_silent(this.getTestMap_afterInitialize(),
                p -> p.getKey().equals(action.getClass()));

        if (null != a4t) {
            a4t.advancedProcessAction((T) action);
        }
    }

    public AbstractActionEntity() {

    }

    public AbstractActionEntity(final Long plan_id) {
        this.setEntity_id(plan_id);
    }

    private final Class findActionClass(final Integer action_code) {
        // РѕР±СЂР°С‰Р°РµРјСЃСЏ Рє РєРѕР»Р»РµРєС†РёРё РєР»Р°СЃСЃРѕРІ-РґРµР№СЃС‚РІРёР№

        //return getAcc().findActionClass(action_code);
        return null;
    }

    //==========================================================================
    @Override
    public Action execEntityAction(
            final Integer action_code_id,
            final Boolean silentExec,
            final ActionEntity oldEntity,
            final Long user_id,
            final String user_ip) {

        return (Action) NullSafe.create()
                .execute2result(() -> {

                    final Class execActClass = this.findActionClass(action_code_id);

                    if (NullSafe.isNull(execActClass)) {

                        LogService.LogErr(this.getClass(), LogService.getCurrentObjProcName(this), () -> String.format("Action is not found (action_code_id=%d)", action_code_id));

                        //throw new ActionClassIsNotFound(String.format("Action is not found (action_code_id=%d)", action_code_id));
                    }
                    // действие не может быть выполенено
//                    if (!this.isAllowedAction(execActClass)) {
//
//                        LogService.LogWarn(execActClass, () -> String.format("%s: %s", execActClass.getName(), SysConst.NOT_ALLOWED_ACTION));
//                        return null;
//                    }

                    return NullSafe.create(NullSafe.createObject(execActClass))
                            .safeExecute((ns_Action) -> {

//                                NullSafe.create()
//                                        .execute(() -> {
//                                            LogService.LogInfo(execActClass, () -> String.format("Action is started (%s, %d)",
//                                                    execActClass.getCanonicalName(),
//                                                    action_code_id));
//                                            ((Action) ns_Action).assignEntity(this)
//                                                    .assignOldEntity(oldEntity)
//                                                    .assignUserId(user_id)
//                                                    .assignUserIp(user_ip)
//                                                    .initialize();
//
//                                            if (silentExec) {
//                                                ((Action) ns_Action).silentExecute();
//                                                this.resetJustCreatedFlag();
//                                            } else {
//                                                //диалог запроса даты расчета
//                                                final String askDateDialog = ((Action) ns_Action).getAskDialogName();
//
//                                                NullSafe.create(askDateDialog)
//                                                        .safeExecute((ns_askDateDialog) -> {
//                                                            AbstractController.showJsfForm((String) ns_askDateDialog);
//                                                        }).whenIsNull(() -> {
//                                                    ((Action) ns_Action).executeWithPreView();
//                                                    return null;
//                                                });
//                                            }
//                                        })
//                                        .finallyBlock(() -> {
//                                            LogService.LogInfo(execActClass, () -> String.format("Execution is finished (actRefID - %d)", action_code_id));
//                                        });
                            })
                            .throwException()
                            .<T>getObject();

                })
                .throwException()
                .<T>getObject();
    }

    //==========================================================================
    private Boolean authorized;

    @Override
    public Boolean getIsAuthorized() {
        return this.authorized;
    }

    @Override
    public void setIsAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    @Override
    public void saveEntityInstance() {
        //this.getAttrsCollection().store(this.getEntity_id());
    }

    //==========================================================================
    private EntityMarkList entityMarks;

    public EntityMarkList getEntityMarks() {

        this.entityMarks = NullSafe.create(this.entityMarks)
                .whenIsNull(() -> {
                    return new EntityMarkList(this.getEntity_id());
                }).<EntityMarkList>getObject();

        return this.entityMarks;
    }

    //==========================================================================
    private EntityWarningsList entityWarningsList;

    @Override
    @JsonIgnore
    public EntityWarningsList getWarningsList(final Boolean recreateWarnig) {

        //entityWarningsList = null;
        NullSafe.create(this.entityWarningsList)
                .whenIsNull(() -> {
//                    final BusinessRule businessRule
//                            = ServiceLocator
//                                    .find(EntityBusinessRulesListService.class)
//                                    .findBusinessRule(this.getClass());

//                    if (NullSafe.notNull(businessRule)) {
//                        this.entityWarningsList = (EntityWarningsList) businessRule.getEntityWarningsList((ActionEntity) this);
//                    }
                });
        return entityWarningsList;
    }

    //==========================================================================
    public void persistEntity() {

    }

    //==========================================================================
    protected void initEntityInstance() {

    }

    //==========================================================================
    protected void readEntityInstance() {

    }

    private Boolean justCreated;

    @JsonIgnore
    @Override
    public Boolean getJustCreated() {
        return justCreated;
    }

    @Override
    public void refreshEntity() throws InternalAppException {
        this.readEntityInstance();
        //this.getAttrsCollection().refresh(this.entity_id);
    }

}
