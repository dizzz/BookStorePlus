package com.bms.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface CartMapper {
    @Insert("insert into TemporaryCart(CreateTime,BookId,UserId} values" +
            "(#{createTime},#{bookId},#{userId}")
    public void addCartItem(@Param("createTime")String createTime,@Param("bookId")Integer bookId,@Param("userId")Integer userId);
    @Delete("delete from TemporaryCart where Id = #{id}")
    public void delCartItem(@Param("id")Integer id);
    @Select("select * from CartView where UserId = #{userid}")
    @Results(value = {
            @Result(id = true, column = "Id", property = "id"),
            @Result(column = "UserId", property = "userId"),
            @Result(column = "BookId", property = "bookId"),
            @Result(column = "CreateTime", property = "createTime"),
            @Result(column = "BookTitle", property = "bookTitle")
    })
    void queryByUserId(@Param("userid")Integer userid);
}
