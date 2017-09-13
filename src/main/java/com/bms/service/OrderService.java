package com.bms.service;

import com.bms.mapper.OrderMapper;
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
    public List<Order> quaryAllOrders(){
        return orderMapper.quaryAllOrders();
    }
    public List<CartItem>quaryOrderItemByOrderId(Integer orderId){
        return orderMapper.quaryOrderItemByOrderId(orderId);
    }
    public Order quaryOrderById(Integer id){
        return orderMapper.quaryOrderById(id);
    }
    public List<Order> quaryOrderByUserId(Integer userId){
        return orderMapper.quaryOrderByUserId(userId);
    }
}
