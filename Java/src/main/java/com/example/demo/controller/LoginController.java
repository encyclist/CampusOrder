package com.example.demo.controller;

import com.example.demo.entity.User;
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
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        log.info("login入参user:{}", user);
        Map<String, Object> map = Maps.newHashMap();
        User u = userService.loginUser(user);
        try {
            if (u.getId() != null && u.getId() != 0) {
                if(u.getState().equals("1")) {
                    if (u.getAuthority().equals("1")) {
                        map.put("state", "success");
                        map.put("msg", "管理员");
                    } else {
                        map.put("state", "fail");
                        map.put("msg", "此用户不是管理员，没有权限进入！");
                    }
                }else{
                    map.put("state", "fail");
                    map.put("msg", "该用户已被限制！");
                }
            }
        } catch (Exception e) {
            map.put("state", "fail");
            map.put("msg", "用户名密码错误！");
        }
        return map;
    }
}
