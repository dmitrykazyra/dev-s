/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.actions;

import com.kdg.fs24.config.SecurityConst;
import com.kdg.fs24.entity.core.AbstractAction;
import com.kdg.fs24.entity.core.api.ActionCodeId;
import com.kdg.fs24.spring.security.ApplicationRole;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
@Table(name = "core_Actions")
@ActionCodeId(action_code = SecurityConst.ACT_CREATE_OR_MODIFY_ROLE,
        action_name = "Регистрация или изменение роли")
public class ActCreateOrModifyRole extends AbstractAction<ApplicationRole> {

    @Override
    protected void doUpdate() {
        this.getPersistenceObjects().add(this.getEntity());
    }

}
