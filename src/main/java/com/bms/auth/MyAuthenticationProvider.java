package com.bms.auth;

import com.bms.auth.CustomWebAuthenticationDetails;
import com.bms.auth.ImageCodeIllegalException;
import com.bms.model.MyUserDetails;
import com.bms.service.MyUserDetailsService;
import groovy.util.logging.Log4j;
import org.apache.log4j.Logger;
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

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    private static  final transient Logger log = Logger.getLogger(Log4j.class);
    @Autowired
    private MyUserDetailsService userService;
    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
        String imageCode = details.getImageCode();
        String session_imageCode = details.getSession_imageCode();
        log.info(imageCode + " " + session_imageCode);
        if(imageCode == null || session_imageCode == null) {
            throw new ImageCodeIllegalException("验证码错误");
        }

        if(!imageCode.equals(session_imageCode)) {
            throw new ImageCodeIllegalException("验证码错误");
        }
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
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password,authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}