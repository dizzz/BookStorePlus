package com.bms.mapper;

import com.bms.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.function.BinaryOperator;

@Mapper
public interface BookMapper {
    @Select("select * from BookView")
    @Results(value={
            @Result(id=true, column="Id", property="id"),
            @Result(column="Title", property="title"),
            @Result(column="Author", property="author"),
            @Result(column="PublishDate", property="publishDate"),
            @Result(column="ISBN", property="ISBN"),
            @Result(column="Price", property="price"),
            @Result(column="Description", property="description"),
            @Result(column="TOC", property="TOC"),
            @Result(column="Clicks", property="clicks"),
            @Result(column="PublishHouse", property="publishHouse")

    })
    List<Book> queryAllBooks();

    @Select("select * from BookView where Id = #{id}")
    @Results(value={
            @Result(id=true, column="Id", property="id"),
            @Result(column="Title", property="title"),
            @Result(column="Author", property="author"),
            @Result(column="PublishDate", property="publishDate"),
            @Result(column="ISBN", property="ISBN"),
            @Result(column="Price", property="price"),
            @Result(column="Description", property="description"),
            @Result(column="TOC", property="TOC"),
            @Result(column="Clicks", property="clicks"),
            @Result(column="PublishHouse", property="publishHouse")

    })
    Book queryBookById(@Param("id")Integer id);
    @Select("select * from BookView where ISBN = #{ISBN}")
    @Results(value={
            @Result(id=true, column="Id", property="id"),
            @Result(column="Title", property="title"),
            @Result(column="Author", property="author"),
            @Result(column="PublishDate", property="publishDate"),
            @Result(column="ISBN", property="ISBN"),
            @Result(column="Price", property="price"),
            @Result(column="Description", property="description"),
            @Result(column="TOC", property="TOC"),
            @Result(column="Clicks", property="clicks"),
            @Result(column="PublishHouse", property="publishHouse"),
    })
    Book queryBookByISBN(@Param("ISBN")String ISBN);
    @Select("select * from BookView where Title like '%${qword}%' union " +
            "select * from BookView where Author like '%${qword}%' union " +
            "select * from BookView where ISBN = #{qword}")
    @Results(value={
            @Result(id=true, column="Id", property="id"),
            @Result(column="Title", property="title"),
            @Result(column="Author", property="author"),
            @Result(column="PublishDate", property="publishDate"),
            @Result(column="ISBN", property="ISBN"),
            @Result(column="Price", property="price"),
            @Result(column="Description", property="description"),
            @Result(column="TOC", property="TOC"),
            @Result(column="Clicks", property="clicks"),
            @Result(column="PublishHouse", property="publishHouse")
    })
    List<Book> queryBooksByKey(@Param("qword")String qword);
    @Select("select * from BookView order by Clicks desc")
    @Results(value = {
            @Result(id=true, column="Id", property="id"),
            @Result(column="Title", property="title"),
            @Result(column="Author", property="author"),
            @Result(column="PublishDate", property="publishDate"),
            @Result(column="ISBN", property="ISBN"),
            @Result(column="Price", property="price"),
            @Result(column="Description", property="description"),
            @Result(column="TOC", property="TOC"),
            @Result(column="Clicks", property="clicks"),
            @Result(column="PublishHouse", property="publishHouse")
    })
    List<Book>queryBookOrderByClicks();
    @Select("select * from BookView order by PublishDate desc")
    @Results(value = {
            @Result(id=true, column="Id", property="id"),
            @Result(column="Title", property="title"),
            @Result(column="Author", property="author"),
            @Result(column="PublishDate", property="publishDate"),
            @Result(column="ISBN", property="ISBN"),
            @Result(column="Price", property="price"),
            @Result(column="Description", property="description"),
            @Result(column="TOC", property="TOC"),
            @Result(column="Clicks", property="clicks"),
            @Result(column="PublishHouse", property="publishHouse")
    })
    List<Book>queryBookOrderByPublishDate();
    @Select("select * from BookView where CategoryId = #{tag}")
    @Results(value={
            @Result(id=true, column="Id", property="id"),
            @Result(column="Title", property="title"),
            @Result(column="Author", property="author"),
            @Result(column="PublishDate", property="publishDate"),
            @Result(column="ISBN", property="ISBN"),
            @Result(column="Price", property="price"),
            @Result(column="Description", property="description"),
            @Result(column="TOC", property="TOC"),
            @Result(column="Clicks", property="clicks"),
            @Result(column="PublishHouse", property="publishHouse")
    })
    List<Book> queryBooksByTag(@Param("tag")Integer tag);
    @Select("select * from OrderBookExtend order by Quantity desc")
    @Results(value = {
            @Result(column = "BookId",property = "id"),
            @Result(column="Title", property="title"),
            @Result(column="Author", property="author"),
            @Result(column="ISBN", property="ISBN"),
            @Result(column="Price", property="price"),
            @Result(column="Description", property="description"),
            @Result(column="TOC", property="TOC"),
            @Result(column="PublishDate", property="publishDate"),
            @Result(column="Clicks", property="clicks"),
            @Result(column="PublishHouse", property="publishHouse")
    })
    List<Book>queryBookOrderBySell();
    @Select("select * from OrderBookExtend order by '%${order}%' desc")
    @Results(value = {
            @Result(column = "BookId",property = "id"),
            @Result(column="Title", property="title"),
            @Result(column="Author", property="author"),
            @Result(column="ISBN", property="ISBN"),
            @Result(column="Price", property="price"),
            @Result(column="Description", property="description"),
            @Result(column="TOC", property="TOC"),
            @Result(column="PublishDate", property="publishDate"),
            @Result(column="Clicks", property="clicks"),
            @Result(column="PublishHouse", property="publishHouse")
    })
    List<Book>queryBookInOrder(@Param("order")String order);
    @Select("select top 5 * from BookView where (BookView.CategoryId in (select OrderBookUser.CategoryId from OrderBookUser where OrderBookUser.userId = #{userId})" +
            "            or BookView.Author in (select OrderBookUser.Author from OrderBookUser where OrderBookUser.userId = #{userId}))" +
            "            and BookView.Id not in(select OrderBookUser.BookId from OrderBookUser where OrderBookUser.userId = #{userId})" +
            "and BookView.Id not in(select TemporaryCart.BookId from TemporaryCart where TemporaryCart.UserId = #{userId})" +
            "            order by Clicks desc")
    @Results(value = {
            @Result(column = "Id",property = "id"),
            @Result(column="Title", property="title"),
            @Result(column="Author", property="author"),
            @Result(column="ISBN", property="ISBN"),
            @Result(column="Price", property="price"),
            @Result(column="Description", property="description"),
            @Result(column="TOC", property="TOC"),
            @Result(column="PublishDate", property="publishDate"),
            @Result(column="Clicks", property="clicks"),
            @Result(column="PublishHouse", property="publishHouse")
    })
    List<Book>queryRecomdBook(@Param("userId")Integer userId);
    @Insert("insert into Books(Title, Author,PublisherId,PublishDate,ISBN,UnitPrice,ContentDescription,TOC,CategoryId,Clicks) " +
            "values (#{title}, #{author},#{publishid},#{publishdate},#{ISBN},#{price},#{decription},#{TOC},#{categoryid},0)")
    void addBook(@Param("title") String title, @Param("author") String author, @Param("publishid") Integer publishid,
                @Param("publishdate") String publishDate, @Param("ISBN")String ISBN,@Param("price") double price,@Param("decription")String description,
                @Param("TOC")String toc,@Param("categoryid")Integer categoryid);


    @Update("update Books set Title=#{title}, Author=#{author},PublisherId=#{publishid},PublishDate=#{publishdate},ISBN=#{ISBN},UnitPrice=#{price}," +
            "ContentDescription=#{decription},TOC=#{TOC},CategoryId=#{categoryid} where Id = #{id}")
    void updateBookById(@Param("id")Integer id,@Param("title") String title, @Param("author") String author, @Param("publishid") Integer publishid,
                @Param("publishdate") String publishDate, @Param("ISBN")String ISBN,@Param("price") double price,@Param("decription")String description,
                @Param("TOC")String toc,@Param("categoryid")Integer categoryid);

    @Delete("delete from Books where Id = #{id}")
    void delete(@Param("id") Integer id);
    @Update("update Books set ContentDescription=#{description} where Id = #{id}")
    void updateDescription(@Param("description")String description,@Param("id") Integer id);
    @Update("update Books set Clicks = Clicks + 1 where Id=#{bookId}")
    void addClicks(@Param("bookId")Integer bookId);
    ///Category///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Select("select * from Categories")
    @Results(value = {
            @Result(id=true,column = "Id",property = "id"),
            @Result(column = "Name",property = "name")
    })
    List<Category>queryAllCategories();
    @Select("select * from Categories where Id = #{id}")
    @Results(value = {
            @Result(id=true,column = "Id",property = "id"),
            @Result(column = "Name",property = "name")
    })
    Category queryCategoryById(@Param("id") Integer id);
    @Insert("insert into Categories(Name) values (#{name})")
    void addCategory(@Param("name") String name);
    @Delete("delete from Categories where Id = #{id}")
    void delCategory(@Param("id") Integer id);
    @Update("update Categories set Name=#{name} where Id = #{id}")
    void updateCategory(@Param("id") Integer id,@Param("name") String name);
    ///Publisher///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//其实这部分可以重用 但是这样写能让结构更加清晰
    @Select("select * from Publishers")
    @Results(value = {
            @Result(id=true,column = "Id",property = "id"),
            @Result(column = "Name",property = "name")
    })
    List<Publisher>queryAllPublishers();
    @Select("select * from Publishers where Id = #{id}")
    @Results(value = {
            @Result(id=true,column = "Id",property = "id"),
            @Result(column = "Name",property = "name")
    })
    Publisher queryPublisherById(@Param("id") Integer id);
    @Insert("insert into Publishers(Name) values (#{name})")
    void addPublisher(@Param("name") String name);
    @Delete("delete from Publishers where Id = #{id}")
    void delPublisher(@Param("id") Integer id);
    @Update("update Publishers set Name=#{name} where Id = #{id}")
    void updatePublisher(@Param("id") Integer id,@Param("name") String name);
    //////Rating/////////////////////////////////////////////
    @Select("select * from BookRatingExtend where BookId = #{bookId}")
    @Results(value = {
            @Result(id=true,column = "Id",property = "id"),
            @Result(column = "BookId",property = "bookId"),
            @Result(column = "LoginId",property = "userName"),
            @Result(column = "UserId",property = "userId"),
            @Result(column = "Rating",property = "rating"),
            @Result(column = "Comment",property = "comment"),
            @Result(column = "CreatedTime",property = "createdTime")
    })
    List<BookRating>queryBookRatingByBookId(@Param("bookId")Integer bookId);
    @Insert("insert into BookRatings(BookId,UserId,Rating,Comment,CreatedTime) values" +
            "(#{bookId},#{userId},#{rating},#{comment},#{createdTime})")
    void addBookRating(@Param("bookId")Integer bookId,@Param("userId")Integer userId,
                       @Param("rating")Integer rating,@Param("comment")String comment,@Param("createdTime")String createdTime);
    @Delete("delete from BookRatings where Id = #{id}")
    void delBookRatingById(@Param("id")Integer id);
}
//TODO 推荐图书不完整