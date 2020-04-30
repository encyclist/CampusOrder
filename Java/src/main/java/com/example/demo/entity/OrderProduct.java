package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProduct {
    private Product product;
    private Long num;

    @Override
    public String toString() {
        return "OrderProduct{" +
                "product=" + product +
                ", num=" + num +
                '}';
    }
}
