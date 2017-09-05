package com.bms.service;

import com.bms.mapper.BookMapper;
import com.bms.model.Book;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;
    public List<Book>quary(int pagesize,int pagestart){
        PageHelper.startPage(1,20);
        return bookMapper.queryAll();
    }
    public void add(Book book){
        bookMapper.insert(book.getTitle(),book.getAuthor(),
                book.getPublishHouse(),book.getPublishDate(),book.getPrice());
    }
    public void del(String title){
        bookMapper.delete(title);
    }
}
