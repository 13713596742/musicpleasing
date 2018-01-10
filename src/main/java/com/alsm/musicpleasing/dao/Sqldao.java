package com.alsm.musicpleasing.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
@Mapper
public interface Sqldao {
    @Select("${sql}")
    public void creatTable(@Param("sql") String sql);
}
