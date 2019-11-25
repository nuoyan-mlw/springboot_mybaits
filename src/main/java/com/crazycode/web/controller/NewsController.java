package com.crazycode.web.controller;

import com.alibaba.fastjson.JSON;
import com.crazycode.pojo.News;
import com.crazycode.pojo.User;
import com.crazycode.service.NewsService;
import com.crazycode.util.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/check",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> findNews(String username,String password) throws Exception {
        String uFreeze = "user:"+ username + ":freeze";
        String uCount = "user:"+ username + ":count";
        String uTime = "user:"+ username + ":time";


        Map<String,Object> map = new HashMap<>();

        ValueOperations<String, String> strOps = stringRedisTemplate.opsForValue();
        //查询是否冻结
        String ifFreeze = strOps.get(uFreeze);
        System.out.println("ifFreeze----"+ifFreeze);
        if(ifFreeze == "true"){
            Long fTime = strOps.getOperations().getExpire(uFreeze, TimeUnit.HOURS);
            map.put("message","账号被冻结!解冻还需要"+fTime+"小时");
        }else {
            //登陆
            User user = newsService.login(username, password);
            System.out.println("user---"+user);
            if(user != null){
                strOps.set(uCount,"3");
                map.put("message","登陆成功!");
            }else {
                //判断是不是第一次登陆失败
                //得到剩余登陆次数
                String count = strOps.get(uCount);
                System.out.println("uCount---"+count);
                if(count=="3" || count == null){
                    //第一次登陆失败
                    strOps.set(uCount,"2");
                    strOps.set(uTime,"time",24L,TimeUnit.HOURS);
                    map.put("message","登陆失败!在24小时内,你还有2次登陆机会");
                }else {
                    strOps.decrement(uCount);
                    String restTime = strOps.get(uCount);
                    System.out.println("restTime--"+restTime);
                    if(restTime.equals("1")){
                        Long time = strOps.getOperations().getExpire(uTime, TimeUnit.HOURS);
                        System.out.println(time);
                        map.put("message","登陆失败!在"+time+"小时内,你还有1次登陆机会");
                    }else {
                        strOps.set(uFreeze,"true",48L,TimeUnit.HOURS);
                        map.put("message","账号被冻结!解冻还需要48小时!");
                    }
                }
            }

        }


        /*Jedis jedis = JedisUtils.getJedis();
        String message = jedis.get("message");
        if(message == null){
            List<News> news = newsService.queryAllNews();
            String s = JSON.toJSONString(news);
            jedis.set("message",s);
            jedis.close();
            System.out.println("从数据库查询!");
            return s;
        }else{
            System.out.println("从redis查询!");
            return message;
        }*/
        return map;
    }
}
