package com.smallbell.modules.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.PageUtil;
import com.smallbell.common.utils.R;
import com.smallbell.modules.app.dao.FavoriteDao;
import com.smallbell.modules.app.dao.MessageDao;
import com.smallbell.modules.app.dao.ShopDao;
import com.smallbell.modules.app.entity.*;
import com.smallbell.modules.app.form.shop.*;
import com.smallbell.modules.app.service.OrderService;
import com.smallbell.modules.app.service.ShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopServiceImpl implements ShopService {

    @Resource
    private ShopDao shopDao;

    @Resource
    private OrderService orderService;

    @Resource
    private FavoriteDao favoriteDao;

    @Override
    public R getAll(Integer pageNum,Integer pageSize) {

        int[] num = {974,975,976,978,979,982,983,984,985,986};

        List<ProductEntity> productVoList = new ArrayList<>();

        for (int i : num) {
            List<ProductEntity> productEntities = shopDao.selectAll(i);
            productVoList.addAll(productEntities);
        }

        int totalPage = PageUtil.totalPage(productVoList.size(),pageSize);

        List<ProductEntity> page = new ArrayList<>();
        if (pageNum<=totalPage){
            page = CollUtil.page(pageNum, pageSize, productVoList);
        }

        return R.ok().put("total",totalPage).put("products",page);
    }

    @Override
    public R selectById(Integer id,Long userId) {

        ProductDetailsVo productDetailsVo = shopDao.selectById(id);

        productDetailsVo.setIsFavorite(getIsFavorite(id, userId));

        productDetailsVo.setIsCollection(getIsCollection(id,userId));
        return R.ok().put("details",productDetailsVo);
    }

    @Override
    public R selectCategory() {

        List<ProductCategoryEntity> productCategoryEntities = shopDao.selectCategory();

        return R.ok().put("categories",productCategoryEntities);
    }

    @Override
    public R selectByCategory(Integer id,Integer pageNum,Integer pageSize) {
        List<ProductEntity> productEntities = shopDao.selectAll(id);
        int i = PageUtil.totalPage(productEntities.size(), pageSize);
        List<ProductEntity> page = new ArrayList<>();
        if (pageNum<=i){
            page = CollUtil.page(pageNum, pageSize, productEntities);
        }
        return R.ok().put("total",i).put("categoryProducts",page);
    }

    @Override
    public R add(Long userId, ShowProductForm param) {
        Integer productId = param.getId();

        ProductEntity product = shopDao.selectByPrimaryKey(productId);
        if (product==null) return R.error();

        UserShoppingCartExample userShoppingCartExample = new UserShoppingCartExample();
        userShoppingCartExample.createCriteria().andProductIdEqualTo(productId).andUserIdEqualTo(userId);
        List<UserShoppingCartEntity> userShoppingCartList = shopDao.selectByExample(userShoppingCartExample);
        System.out.println(userShoppingCartList);
        UserShoppingCartEntity userShoppingCart = new UserShoppingCartEntity();
        if (userShoppingCartList.size()==1){
            userShoppingCart = userShoppingCartList.get(0);
            Integer amount = userShoppingCart.getAmount();
            userShoppingCart.setAmount(amount+1);
            shopDao.updateByPrimaryKey(userShoppingCart);
        } else {
            userShoppingCart.setUserId(userId);
            userShoppingCart.setProductId(productId);
            userShoppingCart.setAmount(1);
            shopDao.insert(userShoppingCart);
        }
        return R.ok();
    }

    @Override
    public R remove(Long userId, ShowProductForm param) {
        Integer productId = param.getId();

        ProductEntity product = shopDao.selectByPrimaryKey(productId);
        if (product==null) return R.error();

        UserShoppingCartExample userShoppingCartExample = new UserShoppingCartExample();
        userShoppingCartExample.createCriteria().andProductIdEqualTo(productId).andUserIdEqualTo(userId);
        List<UserShoppingCartEntity> userShoppingCartList = shopDao.selectByExample(userShoppingCartExample);
        if (userShoppingCartList==null || userShoppingCartList.isEmpty()) return R.error();

        UserShoppingCartEntity userShoppingCart = userShoppingCartList.get(0);
        Integer amount = userShoppingCart.getAmount();
        Integer userShoppingCartId = userShoppingCart.getId();
        if (amount==1) {
            shopDao.deleteByPrimaryKey(userShoppingCartId);
        } else {
            userShoppingCart.setAmount(amount-1);
            shopDao.updateByPrimaryKey(userShoppingCart);
        }
        return R.ok();
    }

    @Override
    public R buy(Long userId, BuyShoppingCartForm param) {
        Integer[] ids = param.getIds();
        Integer addressId = param.getAddressId();
        Integer[] amounts = param.getAmounts();
        for (int i = 0; i < ids.length; i++) {
            UserShoppingCartEntity userShoppingCart = shopDao.selectShopCartByPirmaryKey(ids[i]);
            shopDao.deleteByPrimaryKey(ids[i]);
            if (userShoppingCart==null) return R.error("出现意外");

            AddShoppingOrderParam addShoppingOrderParam = new AddShoppingOrderParam();
            addShoppingOrderParam.setId(userShoppingCart.getProductId());
            addShoppingOrderParam.setAddressId(addressId);
            addShoppingOrderParam.setAmount(amounts.length>i? amounts[i]:userShoppingCart.getAmount());

            orderService.addOrder(userId,addShoppingOrderParam);
        }

        return R.ok();
    }

    @Override
    public R selectAll(Long userId) {
        List<ProductDetailsVo> productVoList = shopDao.selectAllCart(userId);
        if (productVoList==null)
            return R.error();
        Integer count = productVoList.size();
        return R.ok().put("total",count).put("carts",productVoList);
    }


    int getIsFavorite(Integer id, Long userId) {
        if(id==null || userId == null) {
            return 0;
        }

        UserIdAndProductIdPo po = new UserIdAndProductIdPo(userId,id);

        FavoriteEntity favorite = favoriteDao.selectByUserIdAndProductId(po);
        if(favorite == null) {
            return 0;
        }
        return 1;
    }

    int getIsCollection(Integer id, Long userId) {
        if(id==null || userId == null) {
            return 0;
        }

        UserIdAndProductIdPo po = new UserIdAndProductIdPo(userId,id);

        FavoriteEntity favorite = favoriteDao.selectIsCollection(po);
        if(favorite == null) {
            return 0;
        }
        return 1;
    }


}
