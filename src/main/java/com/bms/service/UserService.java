package com.bms.service;
import com.bms.model.User;

import java.util.List;
import com.bms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User>queryAll(){
        return userMapper.queryAll();
    }
    public User queryUserByLoginId(String loginId){
        return userMapper.queryByLoginId(loginId);
    }
//    public User queryWithPWD(String UserName,String password){
//        return userMapper.queryUserInfoWithPwd(UserName,password);
//    }
    public void addUser(User user){
        userMapper.insert(user.getLoginId(),user.getLoginPwd(),user.getAddress(),user.getMail(),user.getName(),
                user.getPhone(),user.getBirthday(),user.getRegisterIp(),user.getRegisterTime());
    }
    public void deleteUserById(String id) {
        userMapper.deleteUser(id);
    }
    public User queryUserById(Integer id){
        return  userMapper.queryById(id);
    }
    public void updateUserState(Integer userId,Integer state){
        userMapper.updateUserState(userId,state);
    }
}
