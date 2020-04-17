/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.security;

import java.util.Collection;
import lombok.Data;
import javax.persistence.*;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import com.kdg.fs24.entity.core.api.EntityKindId;
import com.kdg.fs24.config.SecurityConst;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import com.kdg.fs24.entity.core.api.ActionClassesPackages;

/**
 *
 * @author N76VB
 */
@Data
@Entity
@Table(name = "core_Roles")
@PrimaryKeyJoinColumn(name = "role_id", referencedColumnName = "entity_id")
@ActionClassesPackages(pkgList = {"com.kdg.fs24.actions"})
@EntityTypeId(entity_type_id = SecurityConst.FS24_ROLE,
        entity_type_name = "Роль в учетной системе")
@EntityKindId(entity_kind_id = SecurityConst.FS24_ROLE_BASE,
        entity_type_id = SecurityConst.FS24_ROLE,
        entity_kind_name = "Стандартная роль комплекса")
public class ApplicationRole extends AbstractActionEntity {

    @Column(name = "role_code")
    private String roleCode;
    @Column(name = "role_name")
    private String roleName;
    @ManyToMany(mappedBy = "userRoles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<ApplicationUser> roleUsers;

    public Long getRole_id() {
        return super.getEntity_id();
    }

}
