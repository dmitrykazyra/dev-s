/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.status;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
public class EntityStatusPK implements Serializable {

    private Integer entityStatusId;

    private Integer entityTypeId;
}
