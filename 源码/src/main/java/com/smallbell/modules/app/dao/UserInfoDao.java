package com.smallbell.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallbell.modules.app.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户信息
 */
@Mapper
public interface UserInfoDao  extends BaseMapper<UserInfoEntity> {

    int saveByMobile(UserInfoEntity mobile);

}
