package com.smallbell.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallbell.modules.app.entity.FeedbackEntity;
import com.smallbell.modules.app.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 反馈
 */
@Mapper
public interface FeedbackDao extends BaseMapper<FeedbackEntity> {
}
