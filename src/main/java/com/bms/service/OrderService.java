package com.bms.service;

import com.bms.mapper.OrderMapper;
import com.bms.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    public Integer getNextOrderId(){
        return orderMapper.getNextOrdersId();
    }
    public void addOrder(OrderItem order){
        int id = orderMapper.getNextOrdersId();
        orderMapper.addtoOrders(id,order.getOrderdate(),order.getUserId(),order.getTotalPrice());
        orderMapper.addtoOrderBook(id,order.getBookId(),order.getQuantity(),order.getUnitPrice());
    }
}
