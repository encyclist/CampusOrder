package com.example.demo.mapper;

import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.service.UserQueryCondition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学生dao层接口
 */
@Mapper
@Repository
public interface StudentMapper {
    /**
     * 学生登录
     *
     * @return 返回学生
     */
    public Student login(Student student);
    public Long addStudent(Student student);
    public void updateStudent(Student student);
}