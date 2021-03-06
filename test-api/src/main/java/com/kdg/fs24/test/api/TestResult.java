/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.test.api;

import com.kdg.fs24.application.core.sysconst.SysConst;

/**
 *
 * @author N76VB
 */
public class TestResult {

    private String logMsg;
    private Integer errCount;
    private String errKeyWord;

    public TestResult() {
        super();
    }

    //==========================================================================
    public String getLogMsg() {
        return logMsg;
    }

    public void addLogMsg(final String logMsg) {
        //this.logMsg = (String) NullSafe.nvl(this.logMsg, "");
        if (null == this.logMsg) {
            this.logMsg = SysConst.EMPTY_STRING;
        }
        this.logMsg = this.logMsg.concat(logMsg);
    }

    public Integer getErrCount() {
        return errCount;
    }

    public void setErrCount(final Integer errCount) {
        this.errCount = errCount;
    }

    public String getErrKeyWord() {
        return errKeyWord;
    }

    public void setErrKeyWord(final String errKeyWord) {
        this.errKeyWord = errKeyWord;
    }

}
