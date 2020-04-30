package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.service.OrderQueryCondition;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductQueryCondition;
import com.example.demo.utils.SaveFile;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@CrossOrigin
@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseBody
    @RequestMapping("/addOrder")
    public Map<String, Object> addOrder(Order order) {
        log.info("addOrder入参order:{}", order);
        Map<String, Object> map = Maps.newHashMap();
        try {
            orderService.addOrder(order);
            map.put("code", 0);
            map.put("msg", "新增订单成功");
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("code", 1);
            map.put("msg", "新增订单失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/payOrder")
    public Map<String, Object> payOrder(long id) {
        log.info("payOrder入参id:{}", id);
        Map<String, Object> map = Maps.newHashMap();
        try {
            orderService.payOrder(id);
            map.put("code", 0);
            map.put("msg", "订单支付成功");
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("code", 1);
            map.put("msg", "订单支付失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/deleteOrder")
    public Map<String, Object> deleteOrder(long id) {
        log.info("deleteOrder入参id:{}", id);
        Map<String, Object> map = Maps.newHashMap();
        try {
            orderService.deleteOrder(id);
            map.put("code", 0);
            map.put("msg", "删除订单成功");
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("code", 1);
            map.put("msg", "删除订单失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/selectOrder")
    public Map<String, Object> selectOrder(OrderQueryCondition condition) {
        log.info("selectOrder入参condition:{}", condition);
        Map<String, Object> map = Maps.newHashMap();
        try {
            map.put("code", 0);
            map.put("msg", "查询订单成功");
            map.put("data", orderService.selectOrder(condition));
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("code", 1);
            map.put("msg", "查询订单失败");
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("/selectOrderById")
    public Map<String, Object> selectOrderById(long id) {
        log.info("selectOrderById入参id:{}", id);
        Map<String, Object> map = Maps.newHashMap();
        try {
            map.put("code", 0);
            map.put("msg", "查询订单成功");
            map.put("data", orderService.selectOrderById(id));
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("code", 1);
            map.put("msg", "查询订单失败");
        }
        return map;
    }

}
