package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private Long id;
    private String tel;
    private String password;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", tel='" + tel + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
