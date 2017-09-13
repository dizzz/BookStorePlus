package com.bms.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private String publishHouse;
    private Integer publisherId;
    private String publishDate;
    private String ISBN;
    private String description;
    private String shortDescription;
    private String TOC;
    private Double price;
    private Integer clicks;
    private Integer categoryId;
    public Book(){
    }

    public Book(Book book){
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publishHouse = book.getPublishHouse();
        this.publisherId = book.getPublisherId();
        this.publishDate =book.getPublishDate();
        this.ISBN = book.getISBN();
        this.description = book.getDescription();
        this.shortDescription = book.getShortDescription();
        this.TOC = book.getTOC();
        this.price = book.getPrice();
        this.clicks = book.getClicks();
        this.categoryId = book.getCategoryId();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishHouse() {
        return publishHouse;
    }

    public void setPublishHouse(String publishHouse) {
        this.publishHouse = publishHouse;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        int p = publishDate.indexOf(' ');
        this.publishDate = publishDate.substring(0,p);
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        int i;
        for(i = 0;i<description.length();i++){
            if(i > 90 && description.charAt(i)== '。'){
                break;
            }
        }
        this.shortDescription = description.substring(0,i)+"...";
    }

    public String getTOC() {
        return TOC;
    }

    public void setTOC(String TOC) {
        this.TOC = TOC;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
