package com.bms.mapper;

import com.bms.model.Book;
import com.bms.model.CartItem;
import com.bms.model.Order;
import com.bms.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Mapper
public interface OrderMapper {
    @Insert("insert into Orders(OrderDate,UserId,TotalPrice) values(#{orderDate},#{userId},#{totalPrice})")
    void addtoOrders(@Param("orderDate")String orderDate,@Param("userId")Integer userId,@Param("totalPrice")Double totalPrice);
    @Insert("insert into OrderBook(OrderID,BookID,Quantity,UnitPrice) values(#{orderId},#{bookId},#{quantity},#{unitPrice})")
    void addtoOrderBook(@Param("orderId") Integer orderId,@Param("bookId")Integer bookId,@Param("quantity")Integer quantity,@Param("unitPrice")Double unitPrice);
    @Select("SELECT IDENT_CURRENT('Orders') + IDENT_INCR('Orders') as NextId")
    @Results(value = {
            @Result(column = "NextId",property = "")
    })
    Integer getNextOrdersId();
    @Select("select * from Orders")
    @Results(value = {
            @Result(id=true,column = "Id",property = "id"),
            @Result(column = "OrderDate",property = "orderDate"),
            @Result(column = "UserId",property = "userId"),
            @Result(column = "TotalPrice",property = "totalPrice")
    })
    List<Order>queryAllOrders();
    @Select("select * from Orders where Id = #{id}")
    @Results(value = {
            @Result(id=true,column = "Id",property = "id"),
            @Result(column = "OrderDate",property = "orderDate"),
            @Result(column = "UserId",property = "userId"),
            @Result(column = "TotalPrice",property = "totalPrice")
    })
    Order queryOrderById(@Param("id")Integer id);

    @Select("select * from Orders where UserId = #{userId}")
    @Results(value = {
            @Result(id=true,column = "Id",property = "id"),
            @Result(column = "OrderDate",property = "orderDate"),
            @Result(column = "UserId",property = "userId"),
            @Result(column = "TotalPrice",property = "totalPrice")
    })
    List<Order> queryOrderByUserId(@Param("userId")Integer userId);
    @Select("select * from OrderBookExtend where OrderID = #{orderId}")
    @Results(value = {
            @Result(column = "BookID",property = "bookId"),
            @Result(column = "Quantity",property = "quantity"),
            @Result(column = "Author",property = "author"),
            @Result(column = "ISBN",property = "ISBN"),
            @Result(column = "PublishDate",property = "publishDate"),
            @Result(column = "PublishHouse",property = "publishHouse"),
            @Result(column = "Title",property = "title")
    })
    List<CartItem>queryOrderItemByOrderId(@Param("orderId")Integer orderId);
}
