/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.security;

import com.kdg.fs24.entity.core.AbstractActionEntity;
import org.springframework.security.core.userdetails.User;
import com.kdg.fs24.spring.security.api.ApplicationUser;
import java.util.Collection;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import com.kdg.fs24.spring.security.api.ApplicationRole;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "core_Users")
public class ApplicationUserImpl extends AbstractActionEntity implements ApplicationUser {

    private Long userId;
    private String password; // BASE-256
    private String name;
    private String phone;
    private String mail;

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinTable(name = "core_User2Role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<ApplicationRoleImpl> userRoles;
}
