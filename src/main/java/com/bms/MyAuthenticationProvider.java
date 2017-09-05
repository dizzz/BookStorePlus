package com.bms;

import ch.qos.logback.classic.pattern.SyslogStartConverter;
import com.bms.model.MyUserDetails;
import com.bms.model.User;
import com.bms.service.MyUserDetailsService;
//import com.bms.service.UserRoleService;
import com.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
//    @Autowired
//    private UserService userService;
    @Autowired
    private MyUserDetailsService userService;
//    @Autowired
//    private UserRoleService userRoleService;
    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        MyUserDetails user;
        try{
            user = (MyUserDetails) userService.loadUserByUsername(username);
        }catch (UsernameNotFoundException e){
            throw new BadCredentialsException("Username not found.");
        }
        if (!password.equals(user.getLoginPwd())) {
            throw new BadCredentialsException("Wrong password.");
        }
//        user.setRoles(userRoleService.quaryWithUserName(username));
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password,authorities);
    }
    
    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}