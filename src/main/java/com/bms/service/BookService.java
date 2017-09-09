package com.bms.service;

import com.bms.mapper.BookMapper;
import com.bms.model.Book;
import com.bms.model.Category;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;
    public List<Book>quary(int pagenum,int pagesize){
        PageHelper.startPage(pagenum,pagesize);
        return bookMapper.queryAllBooks();
    }
    public List<Book>quaryAll(){
        return bookMapper.queryAllBooks();
    }
    public List<Book>quaryBookByKey(String key,int pagenum,int pagesize){
        PageHelper.startPage(pagenum,pagesize);
        return bookMapper.queryByKey(key);
    }
    public List<Book>quaryByTag(Integer tag,int pagenum,int pagesize){
        PageHelper.startPage(pagenum,pagesize);
        return bookMapper.quaryByTag(tag);
    }
    public void addBook(Book book){
        book.setPublisherId(quaryPublisherIdByName(book.getPublishHouse()));
        bookMapper.insert(book.getTitle(),book.getAuthor(),
                book.getPublishHouse(),book.getPublishDate(),book.getPrice());
    }
    public void delBook(String title){
        bookMapper.delete(title);
    }
    public void updateDescription(String des,Integer id){
        bookMapper.updateDescription(des,id);
    }

    public List<Category> quaryAllCategories(){
        return bookMapper.quaryAllCategories();
    }
    public Integer quaryPublisherIdByName(String name){
        return bookMapper.quaryPublisherIdbyName(name).getId();
    }
}
