package com.smallbell.modules.app.service.impl;

import com.smallbell.common.exception.RRException;
import com.smallbell.common.utils.R;
import com.smallbell.modules.app.dao.AddressDao;
import com.smallbell.modules.app.dao.OrderDao;
import com.smallbell.modules.app.dao.ShopDao;
import com.smallbell.modules.app.entity.ProductEntity;
import com.smallbell.modules.app.entity.UserAddressListEntity;
import com.smallbell.modules.app.entity.UserShoppingOrderEntity;
import com.smallbell.modules.app.form.order.UserShoppingOrderVo;
import com.smallbell.modules.app.form.shop.AddShoppingOrderParam;
import com.smallbell.modules.app.form.shop.ProductDetailsVo;
import com.smallbell.modules.app.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private ShopDao shopDao;

    @Resource
    private OrderDao orderDao;

    @Resource
    private AddressDao addressDao;

    @Override
    public R addOrder(Long userId, AddShoppingOrderParam param) {
        Integer productId = param.getId();
        Integer amount = param.getAmount();
        Integer addressId = param.getAddressId();

        ProductDetailsVo productEntity = shopDao.selectById(productId);
        if(productEntity == null) {
            throw  new RRException("商品信息异常");
        }

        UserAddressListEntity userAddressList = addressDao.selectByPrimaryKey(addressId);
        if (userAddressList==null) return R.error();

        UserShoppingOrderEntity userShoppingOrder = new UserShoppingOrderEntity();
        userShoppingOrder.setUserId(userId);
        userShoppingOrder.setAddressId(addressId);
        userShoppingOrder.setName(productEntity.getName());
        userShoppingOrder.setSubhead(productEntity.getSubhead());
        userShoppingOrder.setCategory(productEntity.getCategory());
        userShoppingOrder.setDescription(productEntity.getDescription());
        userShoppingOrder.setPrice(productEntity.getPrice());
        userShoppingOrder.setPicture(productEntity.getPicture());
        userShoppingOrder.setProductId(productId);
        userShoppingOrder.setAmount(amount);

        orderDao.insert(userShoppingOrder);
        return R.ok();
    }

    @Override
    public R getAllOrder(Long userId) {
        List<UserShoppingOrderVo> userShoppingOrderVoList = orderDao.selectByUserId(userId);
        return R.ok().put("order",userShoppingOrderVoList);
    }
}
