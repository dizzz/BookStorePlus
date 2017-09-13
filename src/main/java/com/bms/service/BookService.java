package com.bms.service;

import com.bms.mapper.BookMapper;
import com.bms.model.Book;
import com.bms.model.BookRating;
import com.bms.model.Category;
import com.bms.model.Publisher;
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
    public Book quaryBookById(Integer id){
        return bookMapper.quaryBookById(id);
    }
    public List<Book>quaryByTag(Integer tag,int pagenum,int pagesize){
        PageHelper.startPage(pagenum,pagesize);
        return bookMapper.quaryByTag(tag);
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
    public void updateDescription(String des,Integer id){
        bookMapper.updateDescription(des,id);
    }
    ///Category///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Category> quaryAllCategories(){
        return bookMapper.quaryAllCategories();
    }
    public Category quaryCategoryById(Integer id){return bookMapper.quaryCategoryById(id);}
    public void addCategory(Category category){
        bookMapper.addCategory(category.getName());
    }
    public void delCategory(Integer id){bookMapper.delCategory(id);}
    public void updateCategory(Category category){
        bookMapper.updateCategory(category.getId(),category.getName());
    }
    ///Publisher///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Publisher> quaryAllPublishers(){
        return bookMapper.quaryAllPublishers();
    }
    public Publisher quaryPublisherById(Integer id){return bookMapper.quaryPublisherById(id);}
    public void addPublisher(Publisher publisher){
        bookMapper.addPublisher(publisher.getName());
    }
    public void delPublisher(Integer id){bookMapper.delPublisher(id);}
    public void updatePublisher(Publisher publisher){
        bookMapper.updatePublisher(publisher.getId(),publisher.getName());
    }
    ///Rating///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<BookRating>quaryBookRatingByBookId(Integer bookId){
        return bookMapper.quaryBookRatingByBookId(bookId);
    }
    public void addBookRating(BookRating bookRating){
        bookMapper.addBookRating(bookRating.getBookId(),bookRating.getUserId(),bookRating.getRating(),bookRating.getComment(),bookRating.getCreatedTime());
    }
    public void delBookRatingById(Integer id){
        bookMapper.delBookRatingById(id);
    }
}
