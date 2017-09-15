package com.bms.mapper;
import java.util.ArrayList;
import java.util.List;
import com.bms.model.User;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.security.access.method.P;

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
            @Result(column="UserRole",property = "userRole"),
            @Result(column="UserState",property = "userState")

    })
    List<User> queryAll();
    @Select("select * from UserDetails where LoginId=#{loginId}")
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
            @Result(column="UserRole",property = "userRole"),
            @Result(column="UserState",property = "userState")

    })
    User queryByLoginId(@Param("loginId")String loginid);
    @Select("select * from UserDetails where Id=#{id}")
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
            @Result(column="UserRole",property = "userRole"),
            @Result(column="UserState",property = "userState")

    })
    User queryById(@Param("id")Integer id);


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
    @Update("update Users set UserStateId = #{state} where Id = #{id}")
    void updateUserState(@Param("id")Integer userId,@Param("state")Integer state);
}
