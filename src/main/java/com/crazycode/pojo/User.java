package com.crazycode.pojo;


import lombok.Data;

@Data
public class User {
    private Integer id;
    private String uname;
    private String pwd;
    private Integer sex;
    private String birthday;
    private String idcard;
    private String email;
    private String mobile;
    private String address;
    private Integer utype;
}
