package com.bms.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CartItem extends Book{
    private Integer id;
    private Integer userId;
    private String createTime;
    private Integer bookCnt;
    private Double totalPrice;
    public CartItem(){}
    public CartItem(Book book){
        super(book);
        this.bookCnt = 1;
    }
    public CartItem(Integer userId,Integer bookId,Integer bookCnt){
        this.userId = userId;
        this.bookCnt = bookCnt;
        super.setId(bookId);
        this.setCreateTime();
    }

    public Integer getBookId() {
        return super.getId();
    }

    public void setBookId(Integer bookId) {
        super.setId(bookId);
    }
    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBookTitle() {
        return super.getTitle();
    }

    public void setBookTitle(String bookTitle) {
        super.setTitle(bookTitle);
    }

    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getBookCnt() {
        return bookCnt;
    }

    public void setBookCnt(Integer bookCnt) {
        this.bookCnt = bookCnt;
    }

    public Double getUnitPrice() {
        return super.getPrice();
    }


    public void setUnitPrice(Double unitPrice) {
        super.setPrice(unitPrice);
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setTotalPrice(){
        this.totalPrice = super.getPrice() * this.bookCnt;
    }
    public void setCreateTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = sdf.format(d);
    }
}
