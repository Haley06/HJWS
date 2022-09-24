package com.smallbell.modules.app.dao;

import com.smallbell.modules.app.entity.UserShoppingOrderEntity;
import com.smallbell.modules.app.form.order.UserShoppingOrderVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {
    int insert(UserShoppingOrderEntity entity);

    List<UserShoppingOrderVo> selectByUserId(Long userId);
}
