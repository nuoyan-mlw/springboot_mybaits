package com.crazycode.pojo;

import lombok.Data;

@Data
public class News {
    private Integer id;
    private String title;
    private String content;
    private String create_time;
}
