/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.entity.status.EntityStatus;
import com.kdg.fs24.entity.status.EntityStatusPK;
import com.kdg.fs24.persistence.core.PersistanceEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdg.fs24.spring.core.api.ApplicationRepositoryService;
import com.kdg.fs24.repository.*;
import com.kdg.fs24.spring.security.ApplicationUser;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
@Service
public class SecurityService implements ApplicationRepositoryService {

    @Autowired
    private PersistanceEntityManager persistanceEntityManager;

    public ApplicationUser createUser(final String login,
            final String password,
            final String name,
            final String phone,
            final String mail,
            final EntityStatus userStatus) {

        final ApplicationUser user = NullSafe.createObject(ApplicationUser.class);

        user.setName(name);
        user.setMail(mail);
        user.setPhone(phone);
        user.setPassword(password);
        user.setCreation_date(LocalDateTime.now());
        user.setLogin(login);
        user.setEntityStatus(userStatus);

        return user;
    }
}
