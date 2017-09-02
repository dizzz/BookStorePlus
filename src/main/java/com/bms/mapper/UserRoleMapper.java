package com.bms.mapper;
import com.bms.model.UserRole;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserRoleMapper {
    @Select("select * from userrole")
    @Results(value={
            @Result(id=true, column="id", property="id"),
            @Result(column="username", property="username"),
            @Result(column="role", property="role"),
    })
    List<UserRole> queryAll();

    @Select("select * from userrole where username = #{username}")
    @Results(value={
            @Result(id=true, column="username", property="username"),
            @Result(column="role", property="role")
    })
    List<UserRole> queryRoleWithUserName(@Param("username") String userName);
    //根据用户名和密码查询用户是否存在
    @Select("select * from userrole where username = #{username} and password = #{password}")
    @Results(value={
            @Result(id=true, column="username", property="username"),
            @Result(column="password", property="password")
    })
    List<UserRole> queryUserInfoWithRole(@Param("username") String userName, @Param("password") String password);

    //插入用户，用户注册
    @Insert("insert into user (username, password,id) values (#{username}, #{password},#{id})")
    void insert(@Param("username") String userName, @Param("password") String password, @Param("id") String id);
}
