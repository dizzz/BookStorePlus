package com.bms.mapper;
import java.util.ArrayList;
import java.util.List;
import com.bms.model.User;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper{
    @Select("select * from UserDetails")
    @Results(value={
            @Result(id=true, column="Id",property = "id"),
            @Result(column="LoginId",property = "loginId"),
            @Result(column="LoginPwd",property = "loginPwd"),
            @Result(column="Name",property = "name"),
            @Result(column="Address",property = "address"),
            @Result(column="Phone",property = "phone"),
            @Result(column="Mail",property = "mail"),
            @Result(column="Birthday",property = "birthday"),
            @Result(column="RegisterIp",property = "registerIp"),
            @Result(column="RegisterTime",property = "registerTime"),
            @Result(column="UserRole",property = "userRole")
    })
    List<User> queryAll();

    @Select("select * from UserDetails where LoginId = #{LoginId}")
    @Results(value={
            @Result(id=true, column="LoginId", property="loginId"),
            @Result(column="LoginPwd", property="loginPwd"),
            @Result(column="UserRole",property = "userRole")
    })
    User queryUserInfoWithUserName(@Param("LoginId") String userName);
    //根据用户名和密码查询用户是否存在
    @Select("select * from UserDetails where LoginId = #{username} and LoginPwd = #{password}")
    @Results(value={
            @Result(id=true, column="username", property="username"),
            @Result(column="password", property="password")
    })
    User queryUserInfoWithPwd(@Param("username") String userName, @Param("password") String password);

    //插入用户，用户注册
    @Insert("insert into Users (LoginId, LoginPwd,Mail,Address,Name,Phone,Birthday,RegisterIp,RegisterTime,UserRoleId,UserStateId) " +
            "values (#{username}, #{password},#{email},#{address},#{name},#{phone},#{bday},#{ip},#{date},1,1)")
    void insert(@Param("username") String userName, @Param("password") String password, @Param("address") String address,
                @Param("email") String email, @Param("name") String name, @Param("phone") String phone,
                @Param("bday") String birthday,@Param("ip")String ip,@Param("date")String registerdate);
    //删除用户
    @Delete("delete from Users where Id = #{id}")
    void deleteUser(@Param("id") String id);
//    TODO
//    admin不能删admin
}
