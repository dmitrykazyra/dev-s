/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.test.api;

/**
 *
 * @author N76VB
 */
public class EntityTest {

    private Integer action_code;
    private String action_name;
    private String app_name;

    //==========================================================================
    public Integer getAction_code() {
        return action_code;
    }

    public void setAction_code(final Integer action_code) {
        this.action_code = action_code;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(final String action_name) {
        this.action_name = action_name;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(final String app_name) {
        this.app_name = app_name;
    }
}
