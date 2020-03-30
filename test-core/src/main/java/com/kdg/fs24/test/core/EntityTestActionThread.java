/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.test.core;

import com.kdg.fs24.test.api.TestResult;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import com.kdg.fs24.entity.core.api.Action;
import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.locale.NLS;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.tce.api.StackTraceInfo;
import com.kdg.fs24.tce.api.StopWatcher;
import com.kdg.fs24.application.core.thread.AbstractThread;
import java.time.LocalDateTime;
import com.kdg.fs24.application.core.nullsafe.NullSafe;

/**
 *
 * @author kazyra_d
 */
public final class EntityTestActionThread extends AbstractThread {

    private String actionLog = "Empty body";
    private String logMsg;
    private int errCount = 0;
    private String errKeyWord = "success execution";
    private ActionEntity entity;
    private Integer action_code_id;

    public EntityTestActionThread(final Integer action_code_id, final ActionEntity entity) {
        //super(dependencyThread);
        this.action_code_id = action_code_id;
        this.entity = entity;
        //this.dependencyThread = dependencyThread;
//        this.setName(String.format("%s, ActionCode=%d",
//                ((AbstractActionEntity) entity).getAcc().getAction_name(action_code_id),
//                action_code_id));
    }

    //==========================================================================
    @Override
    protected Boolean canRun() {

        return SysConst.BOOLEAN_TRUE;

    }

    //==========================================================================
    @Override
    public void run() {

        this.internalRun(() -> {

//            LogService.appendExternalPrinter(this::addLogMsg);
            addDeilimiter();
            addLogMsg(String.format("Начало тестирование действия [ %s]", this.getName()));
            addDeilimiter();
            final StopWatcher stopWatcher = StopWatcher.create(this.getName());

            final NullSafe nullSafe = NullSafe.create()
                    .execute(() -> {
                        Action testAction = entity.execEntityAction(
                                action_code_id,
                                SysConst.IS_SILENT_EXECUTE,
                                null,
                                SysConst.SERVICE_USER_ID,
                                "user ip");

                        testAction = null;

                    })
                    .catchException((e) -> {
//                        errCount = 1;
//                        errKeyWord = e.getMessage();
//                        addDeilimiter();
//                        addLogMsg(String.format("Ошибка тестирования действия [%d, %s], %s ms  ",
//                                action_code_id, this.getName(),
//                                ChronoUnit.MILLIS.between(ltActionStart, LocalDateTime.now())));
                        this.logStackTrace(e);

                    })
                    .finallyBlock(() -> {

                        if (errCount == 0) {

                            String lowerLog = this.getActionLog().toLowerCase();
                            int totalLength = lowerLog.length();

                            String[] errKeyWordList = {"java.lang.NullPointerException",
                                ".exception.api.",
                                "SQLException",
                                " ERROR ",
                                "Ошибка",
                                "exception:",
                                "ERROR:",
                                "ERROR",
                                ".exception",
                                "exception",
                                SysConst.NOT_ALLOWED_ACTION};

                            for (final String keyWord : errKeyWordList) {
                                errCount += (totalLength - lowerLog.replace(keyWord.toLowerCase(), SysConst.EMPTY_STRING).length()) / keyWord.length();

                                if (errCount > 0) {
                                    errKeyWord = keyWord;
                                    break;
                                }
                            }
                        }

                        if (errCount == 0) {
                            addDeilimiter();
                            addLogMsg(String.format("Успешное завершение тестирование действия [%d, %s], %s ms",
                                    action_code_id, this.getName(), stopWatcher.getTimeExecMillis()));
                        } else {
                            addDeilimiter();
                            addLogMsg(String.format("Ошибка тестирования действия [%d, %s], %s ms  ",
                                    action_code_id, this.getName(), stopWatcher.getTimeExecMillis()));
                        }

//                        TestResult testResult = entity.getTestResult();
//
//                        if (NullSafe.isNull(testResult)) {
//                            testResult = new TestResult();
//                            entity.addTestResult(testResult);
//                        }

//                        testResult.addLogMsg(this.getActionLog());
//                        this.setActionLog(SysConst.EMPTY_STRING);
//                        //addLogMsg(testResult.getLogMsg());
//
//                        if (NullSafe.nvl(testResult.getErrCount(), 0).equals(Integer.valueOf(0))) {
//                            //testResult.addLogMsg(this.getActionLog());
//                            testResult.setErrCount(this.errCount);
//                            testResult.setErrKeyWord(this.errKeyWord);
//                        }
                    });

            this.setIsValid(!nullSafe.getExceptionFlag());

            if (!this.getIsValid()) {
                this.setExceptionMsg(nullSafe.getExceptionMsg());
                this.setThrowable(nullSafe.getThrowable());
                //this.logStackTrace(NullSafe.getThrowable());
            }
        });
    }

    //==========================================================================
    private void addDeilimiter() {
        addLogMsg("///////////////////////////////////////////////////////////".
                concat("//////////////////////////////////////////////////////"));
    }

    protected String getActionLog() {
        return actionLog;
    }

    protected void setActionLog(final String actionLog) {
        this.actionLog = actionLog;
    }

    private void logStackTrace(final Throwable th) {

        addLogMsg(new StackTraceInfo(th).getStringStackTraceInfo());

    }

    //==========================================================================
    protected void addLogMsg(final String logMsg) {
        this.setActionLog(this.getActionLog().concat(String.format("\n %s: %s", NLS.getStringDateTimeMS(LocalDateTime.now()), logMsg)));
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(final String logMsg) {
        this.logMsg = logMsg;
    }

    public ActionEntity getEntity() {
        return entity;
    }

    public void setEntity(ActionEntity entity) {
        this.entity = entity;
    }
}
