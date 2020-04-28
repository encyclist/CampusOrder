package com.example.demo.service;


import com.example.demo.entity.User;
import com.github.pagehelper.PageInfo;

/**
 * 用户接口层
 */
public interface UserService {
    /**
     * 用户登录
     * @return 返回用户
     */
    User loginUser(User user);

    void addUser(User user);
    void updateUser(User user);
    void deleteUser(long id);
    PageInfo selectUser(UserQueryCondition condition);
}
