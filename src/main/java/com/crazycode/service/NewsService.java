package com.crazycode.service;

import com.crazycode.pojo.News;
import com.crazycode.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsService {
    public List<News> queryAllNews() throws Exception;

    public User login(String username, String password)throws Exception;

}
