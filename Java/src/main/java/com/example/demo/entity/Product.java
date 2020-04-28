package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by DELL on 2020/4/28.
 */
@Getter
@Setter
public class Product {
    private long id;
    private String name;
    private String img;
    private String description;
    private BigDecimal price;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
