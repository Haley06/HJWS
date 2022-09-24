package com.smallbell.modules.app.dao;

import com.smallbell.modules.app.entity.ProductCategoryEntity;
import com.smallbell.modules.app.entity.ProductEntity;
import com.smallbell.modules.app.entity.UserShoppingCartEntity;
import com.smallbell.modules.app.form.shop.ProductDetailsVo;
import com.smallbell.modules.app.form.shop.UserShoppingCartExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopDao {

    List<ProductEntity> selectAll(Integer categoryId);

    ProductDetailsVo selectById(Integer id);

    List<ProductCategoryEntity> selectCategory();

    int insert(UserShoppingCartEntity entity);

    List<UserShoppingCartEntity> selectByExample(UserShoppingCartExample example);

    int updateByPrimaryKey(UserShoppingCartEntity entity);

    ProductEntity selectByPrimaryKey(Integer productId);

    int deleteByPrimaryKey(Integer id);


    UserShoppingCartEntity selectShopCartByPirmaryKey(Integer id);

    List<ProductDetailsVo> selectAllCart(Long userId);

}
