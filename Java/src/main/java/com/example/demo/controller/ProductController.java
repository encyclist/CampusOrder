package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductQueryCondition;
import com.example.demo.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseBody
    @RequestMapping("/addProduct")
    public Map<String, Object> addProduct(@RequestBody Product product) {
        log.info("addProduct入参product:{}", product);
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (product.getId() == null) {
                productService.addProduct(product);
                map.put("msg", "新增餐品成功");
            } else {
                productService.updateProduct(product);
                map.put("msg", "修改餐品成功");
            }
            map.put("code", 0);
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("code", 1);
            if (product.getId() == null) {
                map.put("msg", "新增餐品失败");
            } else {
                map.put("msg", "修改餐品失败");
            }
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/deleteProduct")
    public Map<String, Object> deleteProduct(@RequestBody long id) {
        log.info("deleteProduct入参id:{}", id);
        Map<String, Object> map = Maps.newHashMap();
        try {
            productService.deleteProduct(id);
            map.put("code", 0);
            map.put("msg", "删除餐品成功");
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("code", 1);
            map.put("msg", "删除餐品失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/selectProduct")
    public Map<String, Object> selectProduct(@RequestBody ProductQueryCondition condition) {
        log.info("selectProduct入参id:{}", condition);
        Map<String, Object> map = Maps.newHashMap();
        try {
            map.put("code", 0);
            map.put("msg", "查询餐品成功");
            map.put("data", productService.selectProduct(condition));
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("code", 1);
            map.put("msg", "查询餐品失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/uploadImg")
    public Map<String, Object> uploadImg(@RequestParam MultipartFile file) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            SaveFile.saveFile(file);
            map.put("code", 0);
            map.put("msg", "上传图片成功");
        } catch (Exception e) {
            log.info("e:{}", e);
            map.put("code", 1);
            map.put("msg", "上传图片失败");
        }
        return map;
    }
}
