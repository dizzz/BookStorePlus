package com.bms.service;

import com.bms.model.MyUserDetails;
//import com.bms.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.bms.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

//    @Autowired
//    private UserRoleService userRoleService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.queryUserByLoginId(userName);
        } catch (Exception e) {
            throw new UsernameNotFoundException("user select fail");
        }
        if(user == null){
            throw new UsernameNotFoundException("no user found");
        } else {
            try {
                return new MyUserDetails(user);
            } catch (Exception e) {
                throw new UsernameNotFoundException("user role select fail");
            }
        }
    }

    public void autologin(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}