package com.smallbell.modules.app.service;

import com.smallbell.common.utils.R;
import com.smallbell.modules.app.form.shop.AddShoppingOrderParam;

public interface OrderService {

    R addOrder(Long userId, AddShoppingOrderParam  param);

    R getAllOrder(Long userId);
}
