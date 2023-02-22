package com.yue.ume.model.mapper;

import com.yue.ume.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author YueYue
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-02-17 10:43:34
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




