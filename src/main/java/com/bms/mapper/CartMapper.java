package com.bms.mapper;

import com.bms.model.CartItem;
import org.apache.ibatis.annotations.*;
import org.springframework.security.access.method.P;

import java.util.List;

@Mapper
public interface CartMapper {
    @Insert("insert into TemporaryCart(CreateTime,BookId,UserId) values" +
            "(#{createTime},#{bookId},#{userId})")
    public void addCartItem(@Param("createTime")String createTime,@Param("bookId")Integer bookId,@Param("userId")Integer userId);
    @Delete("delete from TemporaryCart where Id = #{id}")
    public void delCartItem(@Param("id")Integer id);
    @Select("select * from CartView where UserId = #{userid}")
    @Results(value = {
            @Result(id = true, column = "Id", property = "id"),
            @Result(column = "UserId", property = "userId"),
            @Result(column = "BookId", property = "bookId"),
            @Result(column = "CreateTime", property = "createTime"),
            @Result(column = "BookTitle", property = "bookTitle"),
            @Result(column = "Author", property = "author"),
            @Result(column = "UnitPrice", property = "unitPrice")
    })
    List<CartItem> queryByUserId(@Param("userid")Integer userid);
    @Select("select UserId,BookId,BookTitle,Author,ISBN,UnitPrice,COUNT(BookId)as BookCnt from  CartView  where UserId = #{userId} and BookId = #{bookId} group by UserId,BookId,BookTitle,Author,ISBN,UnitPrice")
    @Results(value = {
            @Result(id = true, column = "Id", property = "id"),
            @Result(column = "UserId", property = "userId"),
            @Result(column = "BookId", property = "bookId"),
            @Result(column = "CreateTime", property = "createTime"),
            @Result(column = "BookTitle", property = "bookTitle"),
            @Result(column = "Author", property = "author"),
            @Result(column = "UnitPrice", property = "unitPrice")
    })
    CartItem queryByUserIdAndBookId(@Param("userId")Integer userId,@Param("bookId")Integer bookId);


    @Select("select UserId,BookId,BookTitle,Author,ISBN,UnitPrice,COUNT(BookId)as BookCnt from  CartView  where UserId = #{userId} group by UserId,BookId,BookTitle,Author,ISBN,UnitPrice")
    @Results(value = {
            @Result(column = "UserId",property = "userId"),
            @Result(column = "BookId",property = "bookId"),
            @Result(column = "BookCnt",property = "quantity"),
            @Result(column = "ISBN", property = "ISBN"),
            @Result(column = "BookTitle", property = "bookTitle"),
            @Result(column = "Author", property = "author"),
            @Result(column = "UnitPrice", property = "unitPrice")
    })
    List<CartItem> queryCount(@Param("userId")Integer userId);
    @Delete("delete from TemporaryCart where UserId=#{userId} and BookId = #{bookId}")
    void delCartItemByUserIdAndBookId(@Param("userId")Integer userId,@Param("bookId")Integer bookId);
}
