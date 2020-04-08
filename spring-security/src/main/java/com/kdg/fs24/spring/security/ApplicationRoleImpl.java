/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.security;

import com.kdg.fs24.spring.security.api.ApplicationRole;
import com.kdg.fs24.spring.security.api.ApplicationUser;
import java.util.Collection;
import lombok.Data;
import javax.persistence.*;
import com.kdg.fs24.entity.core.AbstractActionEntity;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "core_Roles")
public class ApplicationRoleImpl extends AbstractActionEntity
        implements ApplicationRole {

    private Long roleId;
    private String roleCode;
    private String roleName;
//    @ManyToMany(mappedBy = "core_User2Role")
//    private Collection<ApplicationUserImpl> roleUsers;

}