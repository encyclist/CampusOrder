package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户实体类
 */
@Getter
@Setter
public class User {
    private Long id;
    private String userName;
    private String password;
    private String tel;
    private String authority;
    private String delete_flag;
    private String state;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", authority='" + authority + '\'' +
                ", delete_flag='" + delete_flag + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
