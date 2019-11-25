package com.crazycode;

import com.crazycode.pojo.News;
import com.crazycode.pojo.User;
import com.crazycode.service.NewsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisMysqlApplicationTests {
    @Autowired
    private NewsService newsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads()throws Exception {
        ValueOperations<String, String> strOps = stringRedisTemplate.opsForValue();
        String count = strOps.get("user:qwe:count");

        System.out.println(count);
    }

    @Test
    public void login()throws Exception{
        User user = newsService.login("qwer", "1111");
        System.out.println(user);
    }

}
