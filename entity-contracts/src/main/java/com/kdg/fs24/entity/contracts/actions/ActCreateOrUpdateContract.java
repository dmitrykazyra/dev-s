/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.contracts.actions;

import com.kdg.fs24.entity.contracts.AbstractEntityServiceContract;
import com.kdg.fs24.entity.core.AbstractAction;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
public abstract class ActCreateOrUpdateContract<T extends AbstractEntityServiceContract>
        extends AbstractAction<T> {

}
