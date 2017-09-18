package com.bms.service;

import com.bms.mapper.OrderMapper;
import com.bms.model.Book;
import com.bms.model.CartItem;
import com.bms.model.Order;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    public Integer getNextOrderId(){
        return orderMapper.getNextOrdersId();
    }
    public void addOrder(Order order){
        int id = orderMapper.getNextOrdersId();
        order.setOrderdate();
        orderMapper.addtoOrders(order.getOrderDate(),order.getUserId(),order.getTotalPrice());
        for(int i = 0;i<order.getItems().size();i++){
            CartItem item = order.getItems().get(i);
            orderMapper.addtoOrderBook(id,item.getBookId(),item.getQuantity(),item.getUnitPrice());
        }
    }
    public List<Order> queryAllOrders(){
        return orderMapper.queryAllOrders();
    }
    public List<Order> queryOrderByKey(String key){
        return orderMapper.queryOrderByKey(key);
    }

    public List<CartItem>queryOrderItemByOrderId(Integer orderId){
        return orderMapper.queryOrderItemByOrderId(orderId);
    }
    public Order queryOrderById(Integer id){
        return orderMapper.queryOrderById(id);
    }
    public List<Order> queryOrderByUserId(Integer userId){
        return orderMapper.queryOrderByUserId(userId);
    }

}
