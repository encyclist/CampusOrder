package com.example.demo.service;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户分页查询条件实体
 */
@Getter
@Setter
public class ProductQueryCondition {
    /**
     * 每页个数
     */
    final Integer pageSize = 10;

    /**
     * 最大页数
     */
    final Integer navigatePages = 6;

    /**
     * 页数
     */
    Integer pageNum;

    String name = null;
    long id;

    @Override
    public String toString() {
        return "ProductQueryCondition{" +
                "pageSize=" + pageSize +
                ", navigatePages=" + navigatePages +
                ", pageNum=" + pageNum +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
