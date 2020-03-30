/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.tce.api;

/**
 *
 * @author kazyra_d
 */
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.ZoneId;

public final class StopWatcher {

    private String unitName;
    private final LocalTime ltBegin;

    public String getUnitName() {
        return this.unitName;
    }

    public StopWatcher(final String unitName) {
        this();
        this.unitName = unitName;
    }

    public StopWatcher() {
        this.ltBegin = LocalTime.now(ZoneId.of("Europe/Moscow"));
        this.unitName = this.getRunnigProcName(5);
    }

    public static StopWatcher create() {
        return new StopWatcher();
    }    
    
    public static StopWatcher create(final String unitName) {
        return new StopWatcher(unitName);
    }

    private String getRunnigProcName(final int index) {
        final StackTraceElement ste = Thread.currentThread()
                .getStackTrace()[index];
        return String.format("%s.%s [%s]",
                ste.getClassName(),
                ste.getMethodName(),
                ste.getFileName());
    }

    public long getTimeExecMillis() {
        return ChronoUnit.MILLIS.between(ltBegin, LocalTime.now());
    }

    public String getTimeExecStringMillis() {
        return String.format("%s: executed in {%d} ms",
                unitName,
                this.getTimeExecMillis());
    }
}
