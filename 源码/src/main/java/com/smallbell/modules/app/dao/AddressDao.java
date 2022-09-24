package com.smallbell.modules.app.dao;

import com.smallbell.modules.app.entity.UserAddressListEntity;
import com.smallbell.modules.app.form.address.UserAddressListExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressDao {
    UserAddressListEntity selectByPrimaryKey(Integer id);

    int insert(UserAddressListEntity entity);

    int updateByPrimaryKey(UserAddressListEntity entity);

    List<UserAddressListEntity> selectByExample(UserAddressListExample example);
}
