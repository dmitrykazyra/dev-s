/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.application.core.sysconst;

/**
 *
 * @author N76VB
 */

import com.kdg.fs24.application.core.nullsafe.NullSafe;

import java.math.BigDecimal;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SysConst {

//    public static Boolean TEST_MODE_RUNNING = Boolean.FALSE;  
//    public static String TEST_MODE_LAST_ERR_MSG = SysConst.STRING_NULL;
    public static final AtomicBoolean DEBUG_MODE = NullSafe.createObject(AtomicBoolean.class);
    public static final AtomicBoolean RUSSIAN_REF_LANG = NullSafe.createObject(AtomicBoolean.class);
    public static final String APPNAME = "fs24";
    public static final String CRLF = "\n";
    public static final String NOT_DEFINED = "?";
    public static final String UNKNOWN = "UNKNOWN";
    public static final String EMPTY_STRING = "";
    public static final String DATETIME_MS_FORMAT = "dd.MM.yyyy HH:mm:ss.SSS";
    public static final String DATETIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_FORMAT_MS = "HH:mm:ss.SSS";
    public static final String APP_LOCALE = "ru";
    public static final String LOG_PKG_NAME = "root-service";

    public static final DateTimeFormatter FORMAT_dd_MM_yyyy__HH_mm_ss_SSS = DateTimeFormatter.ofPattern(DATETIME_MS_FORMAT);
    public static final DateTimeFormatter FORMAT_dd_MM_yyyy__HH_mm_ss = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    public static final DateTimeFormatter FORMAT_dd_MM_yyyy = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter FORMAT_HH_mm_ss = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static final DateTimeFormatter FORMAT_HH_mm_ss_SSS = DateTimeFormatter.ofPattern(TIME_FORMAT_MS);

    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = FORMAT_dd_MM_yyyy;

    // номера сущностей
    public static final int ENT_SERVICE_ENTITY = 100;
    public static final int ENT_SMT_USER = 1000;
    public static final int ENT_TERMINAL = 10000;

    // статусы entity
//    public static final int ENT_STATUS_ACTUAL = 1;
//    public static final int ENT_STATUS_CANCELLED = -1;

//    public static final String defItemValue = "Select action";
//
//    //==========================================================================
//    public static final String dateFormat = "dd.MM.yyyy";
//    public static final Integer BANK_ID = 101;
//    
//    //==========================================================================
    public static final Integer TDR_TERMINAL = 1;

    public static final Boolean IS_SILENT_EXECUTE = Boolean.TRUE;
    public static final Boolean IS_SIMPLE_EXECUTE = Boolean.FALSE;

    public final static Boolean IS_CHECK_PARENT_CLASS = Boolean.TRUE;
    public final static Boolean FINAL_CLASS_ONLY = Boolean.FALSE;

    public final static Boolean USE_AUTO_COMMIT = Boolean.TRUE;
    public final static Boolean NO_AUTO_COMMIT = Boolean.FALSE;

    public final static String NO_CONNECT_STRING = "no connect string defined";

    public final static Boolean IS_DEV_MODE = Boolean.TRUE;
    public final static Boolean IS_ALLOWED_ACTION = Boolean.TRUE;
    public final static Boolean IS_NOT_ALLOWED_ACTION = Boolean.TRUE;
    public final static Boolean CHECK_PARENT_CLASS = Boolean.TRUE;
    public final static Boolean FORCE_RELOAD = Boolean.TRUE;

    public final static Long SERVICE_USER_ID = Long.valueOf(1);
    public final static Long LONG_ZERO = Long.valueOf(0);
    public final static Long LONG_NULL = null;
    public final static Integer INTEGER_ZERO = Integer.valueOf(0);
    public final static Integer INTEGER_ONE = Integer.valueOf(1);
    public final static Integer INTEGER_NULL = null;

    public final static LocalDate LOCALDATE_NULL = null;
    public final static LocalDateTime LOCALDATETIME_NULL = null;
    //public final static Boolean GET_ALL_ACTIONS = Boolean.FALSE;
    //public final static Boolean VIEW_ACTIONS_ONLY = Boolean.FALSE;

    public static final int ES_VALID = 0; // сделка создана
    public static final int ES_CLOSED = 1; // сделка закрыта
    public static final int ES_CANCELLED = -1; // сделка аннулирована

    public static final long MAX_SUMM_FOR_TEST = (long) (Long.MAX_VALUE / 1000000);

    public final static BigDecimal BIGDECIMAL_NULL = null;
    public final static BigDecimal BIGDECIMAL_ZERO = BigDecimal.valueOf(0);
    public final static BigDecimal MAX_BIGDECIMAL = BigDecimal.valueOf(19760000);

    public final static Boolean BOOLEAN_NULL = null;
    public final static Boolean BOOLEAN_TRUE = Boolean.TRUE;
    public final static Boolean BOOLEAN_FALSE = Boolean.FALSE;

    public static final Integer MR_TRADE_IS_ATHORIZED = 1; // сделка авторизована
    public static final String NOT_ALLOWED_ACTION = "action is not allowed for execution";

    //public static final Boolean TEST_MODE = SysConst.BOOLEAN_TRUE;
    public static final Boolean FORCED_RELOAD = Boolean.TRUE;

    public static final String APPLICATION_ADDRESS = SysConst.getCurrentIp();

    public static final Object OBJECT_NULL = null;

    public final static String STRING_NULL = null;
    public final static String STRING_TRUE = "true";
    public final static String STRING_FALSE = "false";
    public final static String STRING_YES = "yes";
    public final static String STRING_NO = "no";
    //==========================================================================
    public final static Class<BigDecimal> BIGDECIMAL_CLASS = BigDecimal.class;
    public final static Class<LocalDate> LOCALDATE_CLASS = LocalDate.class;
    public final static Class<LocalDateTime> LOCALDATETIME_CLASS = LocalDateTime.class;
    public final static Class<String> STRING_CLASS = String.class;
    public final static Class<Boolean> BOOLEAN_CLASS = Boolean.class;
    public final static Class<Integer> INTEGER_CLASS = Integer.class;
    //==========================================================================

    //==========================================================================
    public static final String getCurrentIp() {

        String ip;

        try {
            DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = String.format("%s [%s]",
                    InetAddress.getLocalHost().getHostName(),
                    socket.getLocalAddress().getHostAddress());
        } catch (Throwable th) {
            ip = String.format("cant't determine ip address (%s)", th.getMessage());
        }

        return ip;
    }

}

