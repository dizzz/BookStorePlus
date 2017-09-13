//package com.bms.model;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class OrderItem extends Book{
//    private Integer id;
//    private Integer userId;
////    private Integer bookId;
//    private Integer quantity;
////    private String booktitle;
////    private String ISBN;
//    private String orderdate;
////    private Double unitPrice;
//    private Double totalPrice;
//    public OrderItem(){}
//    public OrderItem(Integer userId, Integer bookId, String booktitle, String ISBN, Integer quantity, Double unitPrice){
//        this.userId=userId;
////        this.bookId=bookId;
//        super.setId(bookId);
//        this.quantity=quantity;
////        this.unitPrice=unitPrice;
//        super.setPrice(unitPrice);
//        this.totalPrice=unitPrice*quantity;
////        this.booktitle = booktitle;
//        super.setTitle(booktitle);
////        this.ISBN=ISBN;
//        super.setISBN(ISBN);
//        this.setOrderdate();
//    }
//    public OrderItem(Integer userId, Integer cnt, Book book){
//        super(book);
//        this.userId = userId;
//        this.quantity = cnt;
//        this.totalPrice = cnt * super.getPrice();
//        this.setOrderdate();
//    }
//    public OrderItem(CartItem cartItem){
//        super((Book)cartItem);
//        super.setId(cartItem.getBookId());
//        this.quantity = cartItem.getQuantity();
//        this.totalPrice = this.quantity * this.getPrice();
//        this.userId = cartItem.getUserId();
//        this.setOrderdate();
//    }
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//    @Override
//    public Integer getId() {
//        return id;
//    }
//@Override
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public Integer getBookId() {
//        return super.getId();
//    }
//
//    public void setBookId(Integer bookId) {
//        super.setId(bookId);
//    }
//
//    public String getBookTitle() {
//        return super.getTitle();
//    }
//
//    public void setBookTitle(String bookTitle) {
//        super.setTitle(bookTitle);
//    }
//
//    public String getOrderdate() {
//        return orderdate;
//    }
//
//    public void setOrderdate(String orderdate) {
//        this.orderdate = orderdate;
//    }
//    public void setOrderdate(){
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        this.orderdate = sdf.format(d);
//    }
//
//    public Double getUnitPrice() {
//        return super.getPrice();
//    }
//
//    public void setUnitPrice(Double unitPrice) {
//        super.setPrice(unitPrice);
//    }
//
//    public Double getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(Double totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//}
