package com.bms.mapper;
import java.util.ArrayList;
import java.util.List;
import com.bms.model.User;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper{
    @Select("select * from user")
    @Results(value={
            @Result(id=true, column="username", property="username"),
            @Result(column="password", property="password"),
            @Result(column="telephone", property="telephone"),
            @Result(column="email", property="email"),
            @Result(column="address", property="address"),
            @Result(column="name", property="name")
    })
    List<User> queryAll();

    @Select("select * from user where username = #{username}")
    @Results(value={
            @Result(id=true, column="username", property="username"),
            @Result(column="password", property="password")
    })
    User queryUserInfoWithUserName(@Param("username") String userName);
    //根据用户名和密码查询用户是否存在
    @Select("select * from user where username = #{username} and password = #{password}")
    @Results(value={
            @Result(id=true, column="username", property="username"),
            @Result(column="password", property="password")
    })
    User queryUserInfoWithPwd(@Param("username") String userName, @Param("password") String password);

    //插入用户，用户注册
    @Insert("insert into user(username, password,email,address,name,telephone) " +
            "values (#{username}, #{password},#{email},#{address},#{name},#{telephone})")
    void insert(@Param("username") String userName, @Param("password") String password, @Param("address") String address,
                @Param("email") String email, @Param("name") String name, @Param("telephone") String telephone);
    //删除用户
    @Delete("delete from user where username = #{username}")
    void deleteUser(@Param("username") String username);
//    TODO
//    admin不能删admin
}
