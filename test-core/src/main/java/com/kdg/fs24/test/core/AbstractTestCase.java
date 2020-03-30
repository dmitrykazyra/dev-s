/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.test.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kdg.fs24.application.core.exception.api.CreateEntityException;
import com.kdg.fs24.test.api.TestCase;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.application.core.locale.NLS;
import com.kdg.fs24.application.core.log.LogService;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.application.core.nullsafe.NullSafe;

/**
 *
 * @author N76VB
 */
public abstract class AbstractTestCase<E extends ActionEntity> implements TestCase<E> {

    private EntityTestActionsThreadFlow threadFlow;
    private String actionLog = "Empty body";

    //==========================================================================
    protected <E> E buildGenericEntity4test(final Class<E> entClass) {
        return (E) this.<E>buildEntity4test(entClass);
    }

    //==========================================================================
    protected <E> E buildEntity4test(final Class<E> entClass) {
        return NullSafe.create()
                .execute2result(() -> {

                    final ActionEntity testEntity = AbstractTestCase.createTestEntity(entClass);

                    testEntity.doInitialTest();
                    return testEntity;
                })
                //                .catchException(e -> {
                //                    this.addLogMsg(String.format("%s: Ошибка создания сущности для теста (entClass=%s, %s)",
                //                            e.getClass().getCanonicalName(),
                //                            entClass.getCanonicalName(),
                //                            e.getMessage()));
                //                })
                .throwException()
                .<E>getObject();
    }

    protected TestCase buildTestCase() {
        return null;
    }

    //==========================================================================
    public static <E extends ActionEntity> E createTestEntity(final Class clazz) {
        final E entity = (E) NullSafe.createObject(clazz, SysConst.LONG_ZERO);
//        final String fieldName = "isTestMode";

        NullSafe.create(entity)
                .safeExecute((ns_entity) -> {
                    // выставляем признак на ентити в режиме тестирования через reflection

//                    NullSafe.create()
//                            .execute(() -> {
//                                Field field = AbstractActionEntity.class
//                                        .getDeclaredField(fieldName);
//                                field.setAccessible(true);
//                                field.set(entity, TestConst.TEST_MODE_RUNNING);
//                                field.setAccessible(false);
//                            });
                })
                .whenIsNull(() -> {

                    throw new CreateEntityException(String.format("create test entity exception (entityClass=%s)",
                            clazz.getName()));

                });

        return entity;
    }

    //==========================================================================
    protected void printFields(final E new_generated_entity) {

        synchronized (this.getClass()) {

//            (new Thread(() -> {
            final Class<?> entityClass = new_generated_entity.getClass();
            final Method[] gettersAndSetters = entityClass.getMethods();
            NullSafe.create()
                    .execute(() -> {

                        Class<?> retType;
                        Boolean is_print;

                        for (Method method : gettersAndSetters) {

                            if (method.getName().startsWith("get")) {
                                retType = method.getReturnType();
                                is_print = Boolean.FALSE;

                                if (retType.equals(java.lang.String.class
                                )) {
                                    is_print = SysConst.BOOLEAN_TRUE;

                                } else {
                                    if (retType.equals(java.lang.Integer.class
                                    )) {
                                        is_print = SysConst.BOOLEAN_TRUE;

                                    } else if (retType.equals(java.math.BigDecimal.class
                                    )) {
                                        is_print = SysConst.BOOLEAN_TRUE;

                                    } else if (retType.equals(java.time.LocalDate.class
                                    )) {
                                        is_print = SysConst.BOOLEAN_TRUE;

                                    } else if (retType.equals(java.time.LocalDateTime.class
                                    )) {
                                        is_print = SysConst.BOOLEAN_TRUE;
                                    }
                                }
                                // дефолтное значение
                                if (is_print) {

                                    Method getMethod = NullSafe.create(SysConst.OBJECT_NULL, NullSafe.DONT_THROW_EXCEPTION)
                                            .execute2result(() -> {
                                                return entityClass.getMethod(method.getName());
                                            })
                                            .<Method>getObject();

                                    NullSafe.create(getMethod)
                                            .safeExecute((ns_getMethod) -> {
                                                // отправляем/вызываем на печать только те методы, которые имеют зеркальные сеттеры
                                                NullSafe.create(entityClass.getMethod(method.getName().replaceFirst("get", "set"), method.getReturnType()))
                                                        // сеттер существует
                                                        .safeExecute((ns_setMethod) -> {

                                                            this.addLogMsg(String.format("%s::%s = %s",
                                                                    method.getName().substring(3, 4).toLowerCase()
                                                                            .concat(method.getName().substring(4)),
                                                                    method.getReturnType().getSimpleName(), ((Method) ns_getMethod).invoke(new_generated_entity)));
                                                        });
                                            })
                                            .whenIsNull(() -> {
                                                LogService.LogWarn(entityClass, () -> String.format("Не найден подходящий getter метод (%s)", method.getName()));
                                                return null;
                                            });
                                }
                            }
                        }
                    });
//            })).start();
        }
    }

    //==========================================================================
    @JsonIgnore
    //@Deprecated
    public void runThreadFlow(final ActionEntity generated_entity, final String actionLog, final Integer[] actList) throws Throwable {

        threadFlow = (EntityTestActionsThreadFlow) NullSafe.nvl(threadFlow, new EntityTestActionsThreadFlow(generated_entity, actionLog, actList));
        // чистим тест-мапу после теста
        //((AbstractActionEntity) generated_entity).resetTestMap();

    }

    //==========================================================================
//    @JsonIgnore
//    public void runThreadFlow(final Entity generated_entity, final String actionLog, final Stream actList) throws Throwable {
//
//        NullSafe.create(this.threadFlow)
//                .whenIsNull(() -> {
//                    this.threadFlow = new EntityTestActionsThreadFlow(generated_entity, actionLog, actList);
//                });
//
//        // чистим тест-мапу после теста
//        ((AbstractEntity) generated_entity).resetTestMap();
//
//    }//==========================================================================
    protected void addLogMsg(final String logMsg) {
        this.setActionLog(this.getActionLog().concat(String.format("\n %s: %s", NLS.getStringDateTimeMS(LocalDateTime.now()), logMsg)));
    }

    //==========================================================================  
    @JsonIgnore
    protected String getActionLog() {
        return actionLog;
    }

    //==========================================================================
    protected void setActionLog(final String actionLog) {
        this.actionLog = actionLog;
    }
}
