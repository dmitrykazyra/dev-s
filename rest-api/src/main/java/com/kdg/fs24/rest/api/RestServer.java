/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.rest.api;

/**
 *
 * @author kazyra_d
 */
public interface RestServer {

    /**
     * запустить шттп сервер
     */
    void start(int serverPort);

    /**
     * остановить шттп сервер
     */
    void stop();
}