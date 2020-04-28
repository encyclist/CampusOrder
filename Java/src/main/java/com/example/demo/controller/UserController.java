package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserQueryCondition;
import com.example.demo.service.UserService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by DELL on 2020/4/25.
 */
@Slf4j
@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping("/addUser")
    public Map<String, Object> addUser(@RequestBody User user) {
        log.info("addUser入参user:{}", user.toString());
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (user.getId() == null || user.getId().equals("")) {
                userService.addUser(user);
                map.put("msg", "新增用户成功");
            } else {
                userService.updateUser(user);
                map.put("msg", "修改用户成功");
            }
            map.put("state", "success");
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("state", "fail");
            map.put("msg", "用户操作失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/deleteUser")
    public Map<String, Object> addUser(@RequestBody long id) {
        log.info("deleteUser入参id:{}", id);
        Map<String, Object> map = Maps.newHashMap();
        try {
            userService.deleteUser(id);
            map.put("state", "success");
            map.put("msg", "删除用户成功");
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("state", "fail");
            map.put("msg", "删除用户失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/selectUser")
    public Map<String, Object> selectUser(@RequestBody UserQueryCondition condition) {
        log.info("selectUser入参condition:{}", condition.toString());
        Map<String, Object> map = Maps.newHashMap();
        try {
            map.put("state", "success");
            map.put("msg", userService.selectUser(condition));
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("state", "fail");
            map.put("msg", "查询用户失败");
        }
        return map;
    }
}
