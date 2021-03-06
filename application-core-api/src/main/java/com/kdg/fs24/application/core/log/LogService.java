/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.application.core.log;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.sysconst.SysConst;
import org.apache.logging.log4j.LogManager;

import java.util.Map;

//import org.apache.log4j.Logger;
//import org.apache.logging.log4j.Logger;

/**
 *
 * @author N76VB
 */
public abstract class LogService {

    public final static String PATH_LOG = "/logMessage";
    public final static String PATH_AUDIT = "/logAudit";
    public final static String PATH_THROWABLE = "/logThrowable";
    public final static String PATH_LOG_ERR = "/logERR";
    public final static String PATH_4PING = "/4ping";
    public final static String REGISTER_REQ = "/regRequest";
    public final static String SERV_MAIL = "/4serviceMail";
    //==========================================================================
    private final Map<String, String> paramsList = ServiceFuncs.<String, String>getOrCreateMap(ServiceFuncs.MAP_NULL);
    protected final Log4jBox LOG4J = NullSafe.createObject(Log4jBox.class);

    public LogService() {
        //ServiceLocator.find(CounterpartiesCardsService.class);
    }

    //==========================================================================    
    public Integer getRegParam(final String paramName, final Integer defaultValue) {
        return Integer.valueOf(this.getRegParam(paramName, String.valueOf(defaultValue)));
    }

    //==========================================================================
    public String getRegParam(final String paramName, final String defaultValue) {

        return NullSafe.create(this.paramsList)
                .safeExecute2result((ns_paramsList) -> {

                    if (this.paramsList.isEmpty()) {
                        synchronized (LogService.class) {
                            if (this.paramsList.isEmpty()) {

                                this.LOG4J.getProperties()
                                        .forEach((k, v) -> {
                                            this.paramsList.put((String) k, (String) v);
                                        });
                            }
                        }
                    }

                    return NullSafe.create(ServiceFuncs.
                            <String, String>getMapValue_silent(this.paramsList,
                                    param -> param.getKey().equals(paramName)))
                            .whenIsNull(() -> defaultValue)
                            .<String>getObject();

                }).<String>getObject();
    }

    public void preDestroy() {
        paramsList.clear();
    }
    //==========================================================================
    private final static String msgMask = SysConst.EMPTY_STRING
            //            .concat("//=========================================================\n")
            .concat(" %s");
//            .concat("//=========================================================\n");

    private final static String msgMask2 = SysConst.EMPTY_STRING
            //.concat("//=========================================================\n")
            .concat(" %s: %s");
//            .concat("//=========================================================\n");

    public final static void LogInfo(final Class clazz, final InfoMessage infoMessage) {
//        if (TestConst.TEST_MODE_RUNNING) {
        new Thread(() -> {
            synchronized (LogService.class) {

                LogManager.getLogger(clazz).info(infoMessage.getMessage());

            }
        }).start();
//        }
    }

    //--------------------------------------------------------------------------
    public final static void LogInfo(final Class clazz, final String procedureName, final InfoMessage infoMessage) {
//        if (TestConst.TEST_MODE_RUNNING) {
        new Thread(() -> {
            synchronized (LogService.class) {
                LogManager.getLogger(clazz).info(infoMessage.getMessage());
            }
        }).start();
//        }
    }

    //--------------------------------------------------------------------------
    public final static void LogWarn(final Class clazz, final InfoMessage infoMessage) {
        new Thread(() -> {
            synchronized (LogService.class) {
                LogManager.getLogger(clazz).warn(infoMessage.getMessage());
            }
        }).start();
    }
    //--------------------------------------------------------------------------
    public final static void LogDebug(final Class clazz, final InfoMessage infoMessage) {
        new Thread(() -> {
            synchronized (LogService.class) {
                LogManager.getLogger(clazz).debug(infoMessage.getMessage());
            }
        }).start();
    }
    //--------------------------------------------------------------------------
    public final static void LogWarn(final Class clazz, final String procedureName, final InfoMessage infoMessage) {
        new Thread(() -> {
            synchronized (LogService.class) {
                LogManager.getLogger(clazz).warn(infoMessage.getMessage());
            }
        }).start();
    }
    //--------------------------------------------------------------------------

    public final static void LogErr(final Class clazz, final InfoMessage infoMessage) {
        new Thread(() -> {
            synchronized (LogService.class) {
                LogManager.getLogger(clazz).error(infoMessage.getMessage());
            }
        }).start();
    }

    //--------------------------------------------------------------------------
    public final static void LogErr(final Class clazz, final String procedureName, final InfoMessage infoMessage) {
        new Thread(() -> {
            synchronized (LogService.class) {
                LogManager.getLogger(clazz).error(infoMessage.getMessage());
            }
        }).start();
    }

    //--------------------------------------------------------------------------
    public final static void LogErr(final Class clazz, final String procedureName, Throwable th) {
        new Thread(() -> {
            synchronized (LogService.class) {
                LogManager.getLogger(clazz).error(procedureName, th);
            }
        }).start();
    }

    //--------------------------------------------------------------------------
    public final static void LogErr(final Class clazz, Throwable th) {
        new Thread(() -> {
            synchronized (LogService.class) {
                LogManager.getLogger(clazz).error("???", th);
            }
        }).start();

    }
    //--------------------------------------------------------------------------

    public final static String getCurrentProcName() {
        return Thread.currentThread()
                .getStackTrace()[2]
                .getMethodName();
    }

    //--------------------------------------------------------------------------
    public final static String getCurrentObjProcName(final Object o) {
        return LogService.getCurrentObjProcName(o, -1);
    }

    //--------------------------------------------------------------------------
    public final static String getCurrentObjProcName(final Object o, final int shift) {
        return String.format("%s.%s",
                o.getClass().getName(),
                Thread.currentThread()
                        .getStackTrace()[2 - shift]
                        .getMethodName());
    }
    //--------------------------------------------------------------------------

    public final static String getCurrentObjProcName(final Class clazz) {
        return String.format("%s.%s", clazz.getName(), Thread.currentThread()
                .getStackTrace()[2]
                .getMethodName());
    }
    //--------------------------------------------------------------------------

    private static String warPackageName;

    public static String getWarPackageName() {

        if (null == warPackageName) {
            warPackageName = LogService.getModuleName(LogService.class.
                    getProtectionDomain().
                    getCodeSource().
                    getLocation().
                    getFile());
        }

        return warPackageName;
    }

    public static String getWarPackageName(final Class clazz) {

        return LogService.getModuleName(clazz.
                getProtectionDomain().
                getCodeSource().
                getLocation().
                getFile());

    }

    private static String getModuleName(final String classUrl) {
        String moduleName = SysConst.EMPTY_STRING;
        int indexOff = classUrl.lastIndexOf("/WEB-INF");
        if (indexOff > 0) {
            String url = classUrl.substring(0, classUrl.lastIndexOf("/WEB-INF"));
            while (moduleName.lastIndexOf(".") < 0) {
                if (!moduleName.isEmpty()) {
                    url = url.substring(0, url.lastIndexOf("/WEB-INF"));
                }
                moduleName = url.substring(url.lastIndexOf("/") + 1);
            }
        } else {
            //LogService.LogWarn(LogService.class, String.format("bad classUrl (%s)", classUrl));
            moduleName = ".";
        }
        return moduleName.substring(0, moduleName.lastIndexOf("."));
    }

}
