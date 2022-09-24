package com.smallbell.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallbell.modules.app.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 *
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

}
