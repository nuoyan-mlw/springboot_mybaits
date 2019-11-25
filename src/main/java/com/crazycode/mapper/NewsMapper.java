package com.crazycode.mapper;

import com.crazycode.pojo.News;
import com.crazycode.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NewsMapper {
    public List<News> queryAllNews() throws Exception;

    public User login(@Param("username") String username, @Param("password") String password)throws Exception;
}
