package com.example.demo.service;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单分页查询条件实体
 */
@Getter
@Setter
public class OrderQueryCondition {
    /**
     * 每页个数
     */
    Integer pageSize;

    /**
     * 最大页数
     */
    final Integer navigatePages = 6;

    /**
     * 页数
     */
    Integer pageNum;
    long student_id;
    long id;

    @Override
    public String toString() {
        return "OrderQueryCondition{" +
                "pageSize=" + pageSize +
                ", navigatePages=" + navigatePages +
                ", pageNum=" + pageNum +
                ", student_id=" + student_id +
                '}';
    }

}
