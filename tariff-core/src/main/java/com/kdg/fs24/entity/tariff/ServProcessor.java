/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.tariff;

/**
 *
 * @author N76VB
 */
@FunctionalInterface
public interface ServProcessor {
    void processServ(TariffPlan2Serv tariffPlan2Serv);
}
