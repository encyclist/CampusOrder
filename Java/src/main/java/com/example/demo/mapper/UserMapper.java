package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.example.demo.service.UserQueryCondition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户dao层接口
 */
@Mapper
@Repository
public interface UserMapper {
    /**
     * 用户登录
     *
     * @return 返回用户
     */
    User loginUser(User user);

    void addUser(User user);
    void updateUser(User user);
    void deleteUser(long id);
    List<User> selectUser(UserQueryCondition condition);
}