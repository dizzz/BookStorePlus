package com.bms.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private Integer id;
    private List<CartItem> items;
    private Integer userId;
    private String orderDate;
    private Double totalPrice;
    public Order(){
        totalPrice = 0.0;
        items = new ArrayList<CartItem>();
    }
    public Order(Integer userId){
        this.userId = userId;
        totalPrice = 0.0;
        items = new ArrayList<CartItem>();
    }
    public void add(CartItem cartItem){
        this.items.add(cartItem);
        totalPrice += cartItem.getTotalPrice();
    }
    public List<CartItem> getItems() {
        return items;
    }
    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public void setOrderdate(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.orderDate = sdf.format(d);
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
