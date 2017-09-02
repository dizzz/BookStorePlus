package com.bms.service;

import com.bms.mapper.UserRoleMapper;
import com.bms.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private MyUserDetailsService myuserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    public List<UserRole> quaryAll(){
        return userRoleMapper.queryAll();

    }

    public List<UserRole> quaryWithUserName(String userName){
        System.out.println("sb:" + userName);
        return userRoleMapper.queryRoleWithUserName(userName);
    }
    public void autologin(String username, String password) {
        UserDetails userDetails = myuserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            logger.debug(String.format("Auto login %s successfully!", username));
        }
    }
}
