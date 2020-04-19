/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.liases.api;

/**
 *
 * @author kazyra_d
 */
import java.util.List;

public interface LiasDebtLoader {

    List<LiasDebt> loadLiasDebts(int entity_id);
}
