/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.persistence.api;

import javax.persistence.Query;
/**
 *
 * @author N76VB
 */
public interface QueryExecutor {
    void execute(Query query);
}
