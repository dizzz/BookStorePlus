package com.bms.model;
import com.bms.model.User;
//import com.bms.model.UserRole;
//import com.bms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class MyUserDetails extends User implements UserDetails{


    public MyUserDetails(User user){
        super(user);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (super.getUserRole() == null) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList("");
        }

        System.out.println(super.getUserRole());
        return AuthorityUtils.commaSeparatedStringToAuthorityList(super.getUserRole());
    }

    @Override
    public String getPassword() {
        return super.getLoginPwd();
    }

    @Override
    public String getUsername() {
        return super.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}