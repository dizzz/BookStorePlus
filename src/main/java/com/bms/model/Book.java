package com.bms.model;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private String publishHouse;
    private String publishDate;
    private String ISBN;
    private String description;
    private String TOC;
    private Double price;
    private Integer clicks;

    public Integer getId() {
        return id;
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
        this.publishDate = publishDate;
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
}
