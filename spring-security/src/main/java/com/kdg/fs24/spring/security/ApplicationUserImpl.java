/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.security;

import org.springframework.security.core.userdetails.User;
import com.kdg.fs24.spring.security.api.ApplicationUser;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author N76VB
 */
public class ApplicationUserImpl extends User implements ApplicationUser {

    public ApplicationUserImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public ApplicationUserImpl(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
