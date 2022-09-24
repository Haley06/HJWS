package com.smallbell.modules.app.service;

import com.smallbell.common.utils.R;
import com.smallbell.modules.app.form.shop.BuyShoppingCartForm;
import com.smallbell.modules.app.form.shop.ShowProductForm;

import java.util.List;

public interface ShopService {

    R getAll(Integer pageNum,Integer pageSize);

    R selectById(Integer id,Long userId);

    R selectCategory();

    R selectByCategory(Integer id,Integer pageNum,Integer pageSize);

    /**
     * 商品添加到购物车
     */
    R add(Long userId, ShowProductForm param);

    /**
     * 商品移除自购物车
     */
    R remove(Long userId, ShowProductForm param);

    /**
     * 从购物车购买
     */
    R buy(Long userId, BuyShoppingCartForm param);

    /**
     * 获取购物车所有商品
     */
    R selectAll(Long userId);
}
