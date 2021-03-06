/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.core.api;

/**
 *
 * @author kazyra_d
 */
public class EntityContractConst {

    public static final Boolean CHECK_PARENT_CLASSES = Boolean.FALSE; // признак проверки родителского класса

    public static final int ACT_SAVE_DEFAULT_ENTITY_FIELDS = 501;
    public static final int ACT_RESET_DEFAULT_ENTITY_FIELDS = 502;

    public static final int ACT_AUTHORIZE_CONTRACT = 100000001;
    public static final int ACT_FINISH_CONTRACT = 100000010;
    public static final int ACT_CANCEL_CONTRACT = 100000011;    
    public static final int ACT_REOPEN_CONTRACT = 100000012;
    public static final int ACT_CALCULATE_TARIFFS = 100000020;
    public static final int ACT_CALCULATE_TARIFFS2 = 100000021;
    
    public static final int ACT_ACCRUE_INTEREST = 100000100;    

}
