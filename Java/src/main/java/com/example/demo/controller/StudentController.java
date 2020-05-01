package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.service.StudentService;
import com.example.demo.service.UserQueryCondition;
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
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> login(Student student) {
        log.info("student:{}", student.toString());
        Map<String, Object> map = Maps.newHashMap();
        try {
            Student stu = studentService.login(student);
            if (stu.getId() != null) {
                map.put("code", 0);
                map.put("msg", "学生登录成功");
                map.put("data", stu);
            } else {
                map.put("code", 1);
                map.put("msg", "用户名或密码错误");
            }
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("code", 1);
            map.put("msg", "用户名或密码错误");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/addStudent")
    public Map<String, Object> addStudent(Student student) {
        log.info("addStudent入参student:{}", student.toString());
        Map<String, Object> map = Maps.newHashMap();
        try {
            studentService.addStudent(student);
            map.put("code", 0);
            map.put("msg", "学生注册成功");
            map.put("data", student);
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("code", 1);
            map.put("msg", "学生注册失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/updateStudent")
    public Map<String, Object> updateStudent(Student student) {
        log.info("updateStudent入参student:{}", student.toString());
        Map<String, Object> map = Maps.newHashMap();
        try {
            if(student.getYzm().equals("123456")) {
                studentService.updateStudent(student);
                map.put("code", 0);
                map.put("msg", "修改密码成功");
                map.put("data", student);
            }else{
                map.put("code", 1);
                map.put("msg", "验证码不正确");
            }
        } catch (Exception e) {
            log.error("e:{}", e);
            map.put("code", 1);
            map.put("msg", "修改密码失败");
        }
        return map;
    }

}
