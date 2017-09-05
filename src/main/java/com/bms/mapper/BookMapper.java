package com.bms.mapper;

import com.bms.model.Book;
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
    List<Book> queryAll();


    @Insert("insert into book(title, author,publishhouse,publishdate,price) " +
            "values (#{title}, #{author},#{publishhouse},#{publishdate},#{price})")
    void insert(@Param("title") String title, @Param("author") String author, @Param("publishhouse") String publishHouse,
                @Param("publishdate") String publishDate, @Param("price") double price);

    @Delete("delete from book where title = #{title}")
    void delete(@Param("title") String title);
}
