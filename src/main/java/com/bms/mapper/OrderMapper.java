package com.bms.mapper;

import com.bms.model.Book;
import com.bms.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.security.access.method.P;

import java.util.List;
@Mapper
public interface OrderMapper {
    @Insert("insert into Orders(Id,OrderDate,UserId,TotalPrice) values(#{id},#{orderDate},#{userId},#{totalPrice})")
    void addtoOrders(@Param("id")Integer id,@Param("orderDate")String orderDate,@Param("userId")Integer userId,@Param("totalPrice")Double totalPrice);
    @Insert("insert into OrderBook(OrderID,BookID,Quantity,UnitPrice) values(#{orderId},#{bookId},#{quantity},#{unitPrice})")
    void addtoOrderBook(@Param("orderId") Integer orderId,@Param("bookId")Integer bookId,@Param("quantity")Integer quantity,@Param("unitPrice")Double unitPrice);
    @Select("SELECT IDENT_CURRENT('Orders') + IDENT_INCR('Orders') as NextId")
    @Results(value = {
            @Result(column = "NextId",property = "")
    })
    Integer getNextOrdersId();
}
