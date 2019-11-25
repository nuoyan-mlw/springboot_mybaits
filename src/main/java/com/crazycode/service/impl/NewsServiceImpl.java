package com.crazycode.service.impl;

import com.crazycode.mapper.NewsMapper;
import com.crazycode.pojo.News;
import com.crazycode.pojo.User;
import com.crazycode.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> queryAllNews() throws Exception {
        return newsMapper.queryAllNews();
    }

    @Override
    public User login(String username, String password) throws Exception {
        return newsMapper.login(username,password);
    }
}
