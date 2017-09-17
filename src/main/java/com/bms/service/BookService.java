package com.bms.service;

import com.bms.mapper.BookMapper;
import com.bms.model.Book;
import com.bms.model.BookRating;
import com.bms.model.Category;
import com.bms.model.Publisher;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;
    public List<Book>query(int pagenum,int pagesize) {
        PageHelper.startPage(pagenum, pagesize);
        return bookMapper.queryAllBooks();
    }
    public List<Book>queryBookInOrder(int pagenum,int pagesize,String type){
        PageHelper.startPage(pagenum, pagesize);
        String order;
//        System.out.println(type);
        if(type == null || "publishDate".equals(type)) {
            order = "PublishDate";
        }else if("sell".equals(type)){
            order = "Quantity";
        }else if("clicks".equals(type)){
            order = "Clicks";
        }else{
            order = "PublishDate";
        }
        return bookMapper.queryBookInOrder(order);
    }
    public List<Book>queryAll(){
        return bookMapper.queryAllBooks();
    }
    public List<Book>queryBookByKey(String key,int pagenum,int pagesize){
        PageHelper.startPage(pagenum,pagesize);
        return bookMapper.queryBooksByKey(key);
    }
    public Book queryBookById(Integer id){
        return bookMapper.queryBookById(id);
    }
    public List<Book>queryBookOrderByClicks(int pagenum,int pagesize){
        PageHelper.startPage(pagenum,pagesize);

        return bookMapper.queryBookOrderByClicks();
    }
    public List<Book>queryBookOrderByPublishDate(int pagenum,int pagesize){
        PageHelper.startPage(pagenum,pagesize);
        return bookMapper.queryBookOrderByPublishDate();
    }
    public List<Book>queryBookOrderBySell(int pagenum,int pagesize){
        PageHelper.startPage(pagenum,pagesize);
        return bookMapper.queryBookOrderBySell();
    }

    public List<Book>queryByTag(Integer tag,int pagenum,int pagesize){
        PageHelper.startPage(pagenum,pagesize);
        return bookMapper.queryBooksByTag(tag);
    }
    public List<Book>queryRecomdBook(Integer userId){
//        PageHelper.startPage(1,5);
        return bookMapper.queryRecomdBook(userId);
    }

    public void addBook(Book book){
        bookMapper.addBook(book.getTitle(),book.getAuthor(),
                book.getPublisherId(),book.getPublishDate(),book.getISBN(),book.getPrice(),
                book.getDescription(),book.getTOC(),book.getCategoryId());
    }
    public void delBook(Integer id){
        bookMapper.delete(id);
    }
    public void updateBook(Book book){
        bookMapper.updateBookById(book.getId(),book.getTitle(),book.getAuthor(),
                book.getPublisherId(),book.getPublishDate(),book.getISBN(),book.getPrice(),
                book.getDescription(),book.getTOC(),book.getCategoryId());
    }
    public Book queryBookByISBN(String ISBN){
        return bookMapper.queryBookByISBN(ISBN);
    }
    public void addClicks(Integer bookId){
        bookMapper.addClicks(bookId);
    }
    public void updateDescription(String des,Integer id){
        bookMapper.updateDescription(des,id);
    }

    ///Category///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Category> queryAllCategories(){
        return bookMapper.queryAllCategories();
    }
    public Category queryCategoryById(Integer id){return bookMapper.queryCategoryById(id);}
    public void addCategory(Category category){
        bookMapper.addCategory(category.getName());
    }
    public void delCategory(Integer id){bookMapper.delCategory(id);}
    public void updateCategory(Category category){
        bookMapper.updateCategory(category.getId(),category.getName());
    }

    ///Publisher///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Publisher> queryAllPublishers(){
        return bookMapper.queryAllPublishers();
    }
    public Publisher queryPublisherById(Integer id){return bookMapper.queryPublisherById(id);}
    public void addPublisher(Publisher publisher){
        bookMapper.addPublisher(publisher.getName());
    }
    public void delPublisher(Integer id){bookMapper.delPublisher(id);}
    public void updatePublisher(Publisher publisher){
        bookMapper.updatePublisher(publisher.getId(),publisher.getName());
    }

    ///Rating///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<BookRating>queryBookRatingByBookId(Integer bookId){
        return bookMapper.queryBookRatingByBookId(bookId);
    }
    public void addBookRating(BookRating bookRating){
        bookMapper.addBookRating(bookRating.getBookId(),bookRating.getUserId(),bookRating.getRating(),bookRating.getComment(),bookRating.getCreatedTime());
    }
    public void delBookRatingById(Integer id){
        bookMapper.delBookRatingById(id);
    }
}
