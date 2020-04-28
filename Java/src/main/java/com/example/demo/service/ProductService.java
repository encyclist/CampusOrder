package com.example.demo.service;

import com.example.demo.entity.Product;
import com.github.pagehelper.PageInfo;

/**
 * Created by DELL on 2020/4/28.
 */
public interface ProductService {
    // 新增商品
    public void addProduct(Product product);
    // 删除商品
    public void deleteProduct(long id);
    // 修改商品
    public void updateProduct(Product product);
    //查询商品
    public PageInfo selectProduct(ProductQueryCondition condition);

}
