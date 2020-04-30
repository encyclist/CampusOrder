package com.example.demo.mapper;

import com.example.demo.entity.OrderProduct;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.ProductQueryCondition;
import com.example.demo.service.UserQueryCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 餐品dao层接口
 */
@Mapper
@Repository
public interface ProductMapper {

    // 新增商品
    public void addProduct(Product product);
    // 删除商品
    public void deleteProduct(long id);
    // 修改商品
    public void updateProduct(Product product);
    //查询商品
    public List<Product> selectProduct(ProductQueryCondition condition);
    //按id集合计算订单总价
    public List<Product> selectProductPriceById(@Param("oplist") List<OrderProduct> oplist);
    //按id查询商品
    public Product selectProductById(long id);
}