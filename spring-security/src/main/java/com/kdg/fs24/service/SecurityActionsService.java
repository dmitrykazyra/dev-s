/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import lombok.Data;
import org.springframework.stereotype.Service;
import com.kdg.fs24.entity.core.api.EntityClassesPackages;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.entity.status.EntityStatusPK;
import com.kdg.fs24.spring.security.*;
import java.time.LocalDateTime;

/**
 *
 * @author N76VB
 */
@Data
@Service
@EntityClassesPackages(pkgList = {"com.kdg.fs24.spring.security"})
public class SecurityActionsService extends ActionExecutionService {

    public ApplicationUser createUser(final String login,
            final String password,
            final String name,
            final String phone,
            final String mail) {

        final ApplicationUser user = NullSafe.createObject(ApplicationUser.class);

        user.setName(name);
        user.setMail(mail);
        user.setPhone(phone);
        user.setPassword(password);
        user.setCreation_date(LocalDateTime.now());
        user.setLogin(login);

        final EntityStatusPK entityStatusPK = NullSafe.createObject(EntityStatusPK.class);

        entityStatusPK.setEntityStatusId(1);
        entityStatusPK.setEntityTypeId(100);

        final EntityStatus userStatus = this.getPersistanceEntityManager()
                .getEntityManager()
                .find(EntityStatus.class, entityStatusPK);

        user.setEntityStatus(userStatus);

        return user;
    }

    //==========================================================================
    public ApplicationRole createRole(final String roleCode,
            final String roleName,
            final String name) {

        final ApplicationRole role = NullSafe.createObject(ApplicationRole.class);

        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        role.setCreation_date(LocalDateTime.now());

        final EntityStatusPK roleStatusPK = NullSafe.createObject(EntityStatusPK.class);

        roleStatusPK.setEntityStatusId(1);
        roleStatusPK.setEntityTypeId(101);

        //this.getPersistanceEntityManager().getEntityManager()
        final EntityStatus roleStatus = this.getPersistanceEntityManager()
                .getEntityManager()
                .find(EntityStatus.class, roleStatusPK);

        role.setEntityStatus(roleStatus);

        return role;
    }
}
