package com.yue.ume.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yue.ume.model.domain.User;
import com.yue.ume.model.mapper.UserMapper;
import com.yue.ume.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author YueYue
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-02-17 10:43:34
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public void fun() {
        this.list();
    }
}




