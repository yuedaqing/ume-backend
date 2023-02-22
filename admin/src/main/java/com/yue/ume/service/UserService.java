package com.yue.ume.service;

import com.yue.ume.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author YueYue
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-02-17 10:43:34
*/
public interface UserService extends IService<User> {

    void fun();
}
