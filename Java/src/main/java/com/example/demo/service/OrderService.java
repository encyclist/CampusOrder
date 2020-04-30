package com.example.demo.service;

import com.example.demo.entity.Order;
import com.github.pagehelper.PageInfo;

public interface OrderService {
    public void addOrder(Order order);
    public void payOrder(long id);
    public void deleteOrder(long id);
    PageInfo selectOrder(OrderQueryCondition condition);
    public Order selectOrderById(long id);
}
