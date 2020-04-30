package com.example.demo.mapper;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderProduct;
import com.example.demo.entity.User;
import com.example.demo.service.OrderQueryCondition;
import com.example.demo.service.UserQueryCondition;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 订单dao层接口
 */
@Mapper
@Repository
public interface OrderMapper {
    public void addOrder(Order order);
    public void addOrderProduct(@Param("order_id")long order_id, @Param("oplist")List<OrderProduct> oplist);
    public void calculatePrice(Order order);
    public void payOrder(long id);
    public void deleteOrder(long id);
    List<Order> selectOrder(OrderQueryCondition condition);
    List<Map> selectOrderProduct(long id);
}