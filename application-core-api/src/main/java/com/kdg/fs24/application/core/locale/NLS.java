/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.application.core.locale;

//import com.kdg.fs24.registry.api.ApplicationSetup;

import com.kdg.fs24.application.core.api.ObjectRoot;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.sysconst.SysConst;
import org.springframework.beans.factory.annotation.Value;

import java.time.*;
import java.time.format.DateTimeFormatter;

public final class NLS extends ObjectRoot {

    //public static final String DATE_FORMAT = "dd.MM.yyyy";
    @Value("${NEW_DATE_FORMAT}")
    public static final String NEW_DATE_FORMAT = SysConst.EMPTY_STRING;
    
    public static final String DATE_FORMAT = NLS.getNlsParam("DATE_FORMAT", "dd.MM.yyyy");
    public static final String DATETIME_MS_FORMAT = NLS.getNlsParam("DATETIME_MS_FORMAT", "dd.MM.yyyy HH:mm:ss.SSS");
    public static final String DATETIME_FORMAT = NLS.getNlsParam("DATETIME_FORMAT", "dd.MM.yyyy HH:mm:ss");
    public static final String TIME_FORMAT = NLS.getNlsParam("TIME_FORMAT", "HH:mm:ss");
    public static final String TIME_FORMAT_MS = NLS.getNlsParam("TIME_FORMAT_MS", "HH:mm:ss.SSS");
    public static final String APP_LOCALE = NLS.getNlsParam("APP_LOCALE", "ru");

    public static final DateTimeFormatter FORMAT_dd_MM_yyyy__HH_mm_ss_SSS = DateTimeFormatter.ofPattern(DATETIME_MS_FORMAT);
    public static final DateTimeFormatter FORMAT_dd_MM_yyyy__HH_mm_ss = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    public static final DateTimeFormatter FORMAT_dd_MM_yyyy = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter FORMAT_HH_mm_ss = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static final DateTimeFormatter FORMAT_HH_mm_ss_SSS = DateTimeFormatter.ofPattern(TIME_FORMAT_MS);

    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = FORMAT_dd_MM_yyyy;

    //==========================================================================
    static {
        LogService.LogInfo(NLS.class, () -> String.format("DF = %s", NLS.DATE_FORMAT));
    }

    public final static String getNlsParam(final String prmName, final String defVal) {

//        return ServiceLocator
//                .find(ApplicationSetup.class)
//                .getRegParam(prmName, defVal);
// временно
        return defVal;

    }

    //==========================================================================
    public final static String getStringDate(final LocalDate ld) {

        return (NullSafe.isNull(ld)) ? SysConst.EMPTY_STRING : ld.format(NLS.FORMAT_dd_MM_yyyy);

    }

    public final static String getStringTime(LocalTime lt) {

        return (NullSafe.isNull(lt)) ? SysConst.EMPTY_STRING : lt.format(NLS.FORMAT_HH_mm_ss);

    }

    public final static String getStringTimeMS(LocalTime lt) {

        return (NullSafe.isNull(lt)) ? SysConst.EMPTY_STRING : lt.format(NLS.FORMAT_HH_mm_ss_SSS);

    }

    //==========================================================================
    public final static String getStringDateTime(final LocalDateTime ldt) {

        return (NullSafe.isNull(ldt)) ? SysConst.EMPTY_STRING : ldt.format(NLS.FORMAT_dd_MM_yyyy__HH_mm_ss);

    }

    //==========================================================================
    public final static String getStringDateTimeMS(final LocalDateTime ldt) {

        return (NullSafe.isNull(ldt)) ? SysConst.EMPTY_STRING : ldt.format(NLS.FORMAT_dd_MM_yyyy__HH_mm_ss_SSS);

    }

    //==========================================================================
    public final static LocalDate long2LocalDate(final Long milliSeconds) {

        LocalDate result = null;

        if (NullSafe.notNull(milliSeconds)) {

            result = Instant.ofEpochMilli(milliSeconds)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        return (result);
    }

    //==========================================================================
    public final static String getObject2String(final Object value) {

        return NullSafe.create(value)
                //                .setResult((Object) SysConst.LONG_ZERO)
                .whenIsNull(() -> {
                    return SysConst.NOT_DEFINED;
                })
                .safeExecute2result((ns_value) -> {
                    final String stringValue;
                    final Class<?> valueClass = ns_value.getClass();

                    if (valueClass.equals(LocalDate.class)) {
                        stringValue = NLS.getStringDate((LocalDate) ns_value);
                    } else {
                        if (valueClass.equals(LocalDateTime.class)) {
                            stringValue = NLS.getStringDateTime((LocalDateTime) ns_value);
                        } else {
                            stringValue = String.format("%s", ns_value);
                        }
                    }

                    return stringValue;
                })
                .<String>getObject();
    }
}
