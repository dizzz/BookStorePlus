package com.bms.service;
import com.bms.model.User;

import java.util.ArrayList;
import java.util.List;
import com.bms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean login(String userName, String password) {
        User user = userMapper.queryUserInfoWithPwd(userName, password);
        if(user == null) {
            return false;
        }
        return true;
    }
    public List<User>quaryAll(){
        return userMapper.queryAll();
    }
    public User quaryWithUserName(String userName){
        return userMapper.queryUserInfoWithUserName(userName);
    }
    public User quaryWithPWD(String UserName,String password){
        return userMapper.queryUserInfoWithPwd(UserName,password);
    }
    public void save(User user){
        userMapper.insert(user.getLoginId(),user.getLoginPwd(),user.getAddress(),user.getMail(),user.getName(),user.getPhone());
    }
    public void deleteByName(String username) {
        userMapper.deleteUser(username);
    }
}
