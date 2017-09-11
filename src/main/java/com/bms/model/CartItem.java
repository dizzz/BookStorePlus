package com.bms.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CartItem {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private String ISBN;
    private String bookTitle;
    private String createTime;
    private Integer bookCnt;
    private String author;
    private Double unitPrice;
    private Double totalPrice;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setTotalPrice(){
        this.totalPrice = this.unitPrice * this.bookCnt;
    }
    public void setCreateTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = sdf.format(d);
    }
}
