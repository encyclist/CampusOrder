package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Order {
    private Long id;
    private Long student_id;
    private String table;
    private BigDecimal price;
    private String state;
    private String create_time;
    private List<OrderProduct> oplist;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", student_id=" + student_id +
                ", table='" + table + '\'' +
                ", price=" + price +
                ", state='" + state + '\'' +
                ", create_time='" + create_time + '\'' +
                ", oplist=" + oplist +
                '}';
    }
}
