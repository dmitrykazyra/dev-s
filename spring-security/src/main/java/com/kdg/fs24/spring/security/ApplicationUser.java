/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.security;

import com.kdg.fs24.config.SecurityConst;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import com.kdg.fs24.entity.core.api.EntityKindId;
import org.springframework.security.core.userdetails.User;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import java.util.Collection;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import com.kdg.fs24.entity.core.AbstractPersistenceEntity;
import lombok.Data;
import com.kdg.fs24.entity.core.api.ActionClassesPackages;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "core_Users")
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "entity_id")
@ActionClassesPackages(pkgList = {"com.kdg.fs24.actions"})
@EntityTypeId(entity_type_id = SecurityConst.FS24_USER,
        entity_type_name = "Пользователь комплекса")
@EntityKindId(entity_kind_id = SecurityConst.FS24_USER_BASE,
        entity_type_id = SecurityConst.FS24_USER,
        entity_kind_name = "Стандартный пользователь комплекса")
public class ApplicationUser extends AbstractActionEntity {

    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password; // BASE-256
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "mail")
    private String mail;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "core_User2Role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<ApplicationRole> userRoles;

    public Long getUser_id() {
        return super.getEntity_id();
    }
}
