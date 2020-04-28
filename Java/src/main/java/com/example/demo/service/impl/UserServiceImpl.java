package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserQueryCondition;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户接口实现层
 */
@Service
public class UserServiceImpl implements UserService {

    final private UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User loginUser(User user) {
        return userMapper.loginUser(user);
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(long id) {
        userMapper.deleteUser(id);
    }

    @Override
    public PageInfo selectUser(UserQueryCondition condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        final List<User> list = userMapper.selectUser(condition);
        return new PageInfo(list, condition.getNavigatePages());
    }
}
