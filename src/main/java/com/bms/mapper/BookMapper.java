package com.bms.mapper;

import com.bms.model.Book;
import com.bms.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface BookMapper {
    @Select("select * from book")
    @Results(value={
            @Result(id=true, column="title", property="title"),
            @Result(column="author", property="author"),
            @Result(column="publishhouse", property="publishHouse"),
            @Result(column="publishdate", property="publishDate"),
            @Result(column="price", property="price")
    })
    List<Book> queryAll();


    @Insert("insert into book(title, author,publishhouse,publishdate,price) " +
            "values (#{title}, #{author},#{publishhouse},#{publishdate},#{price})")
    void insert(@Param("title") String title, @Param("author") String author, @Param("publishhouse") String publishHouse,
                @Param("publishdate") String publishDate, @Param("price") double price);
    //删除用户
}
