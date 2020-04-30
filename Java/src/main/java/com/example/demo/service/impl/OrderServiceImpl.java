package com.example.demo.service.impl;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderProduct;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.OrderQueryCondition;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductQueryCondition;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, ProductMapper productMapper) {
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
    }

    @Transactional
    public void addOrder(Order order) {
        // 添加订单
        orderMapper.addOrder(order);
        // 计算保存订单价格
        List<Product> productList = productMapper.selectProductPriceById(order.getOplist());
        BigDecimal price = BigDecimal.valueOf(0);
        for (int i = 0; i < order.getOplist().size(); i++) {
            for (int j = 0; j < productList.size(); j++) {
                if (order.getOplist().get(i).getProduct().getId().equals(productList.get(j).getId())) {
                    price = price.add(productList.get(j).getPrice().multiply(BigDecimal.valueOf(order.getOplist().get(i).getNum())));
                }
            }
        }
        order.setPrice(price);
        // 更新订单价格
        orderMapper.calculatePrice(order);
        // 添加一对多订单商品关系
        orderMapper.addOrderProduct(order.getId(), order.getOplist());
    }

    @Override
    public void payOrder(long id) {
        orderMapper.payOrder(id);
    }

    @Override
    public void deleteOrder(long id) {
        orderMapper.deleteOrder(id);
    }

    @Override
    public PageInfo selectOrder(OrderQueryCondition condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        final List<Order> list = orderMapper.selectOrder(condition);
        return new PageInfo(list, condition.getNavigatePages());
    }

    @Transactional
    public Order selectOrderById(long id) {
        OrderQueryCondition condition = new OrderQueryCondition();
        condition.setPageSize(1);
        condition.setPageNum(1);
        condition.setId(id);
        Order order = orderMapper.selectOrder(condition).get(0);
        List<OrderProduct> list = new ArrayList<>();
        orderMapper.selectOrderProduct(id).forEach((op) -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setNum(Long.parseLong(op.get("num").toString()));
            orderProduct.setProduct(productMapper.selectProductById(Long.parseLong(op.get("product_id").toString())));
            list.add(orderProduct);
        });
        order.setOplist(list);
        return order;
    }
}
