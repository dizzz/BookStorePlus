package com.bms.mapper;

import com.bms.model.Book;
import com.bms.model.Category;
import com.bms.model.Publisher;
import com.bms.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.security.access.method.P;

import java.util.List;
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
    Book quaryBookById(@Param("id")Integer id);

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
    List<Book> queryByKey(@Param("qword")String qword);

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
    List<Book>quaryByTag(@Param("tag")Integer tag);

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

    ///Category///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Select("select * from Categories")
    @Results(value = {
            @Result(id=true,column = "Id",property = "id"),
            @Result(column = "Name",property = "name")
    })
    List<Category>quaryAllCategories();
    @Select("select * from Categories where Id = #{id}")
    @Results(value = {
            @Result(id=true,column = "Id",property = "id"),
            @Result(column = "Name",property = "name")
    })
    Category quaryCategoryById(@Param("id") Integer id);
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
    List<Publisher>quaryAllPublishers();
    @Select("select * from Publishers where Id = #{id}")
    @Results(value = {
            @Result(id=true,column = "Id",property = "id"),
            @Result(column = "Name",property = "name")
    })
    Publisher quaryPublisherById(@Param("id") Integer id);
    @Insert("insert into Publishers(Name) values (#{name})")
    void addPublisher(@Param("name") String name);
    @Delete("delete from Publishers where Id = #{id}")
    void delPublisher(@Param("id") Integer id);
    @Update("update Publishers set Name=#{name} where Id = #{id}")
    void updatePublisher(@Param("id") Integer id,@Param("name") String name);

}
