package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.shop.BuyShoppingCartForm;
import com.smallbell.modules.app.form.shop.ProductDetailsVo;
import com.smallbell.modules.app.form.shop.ShowProductForm;
import com.smallbell.modules.app.service.ShopService;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/shop")
public class AppShopController {

    @Resource
    private ShopService shopService;

    @RequestMapping(method = RequestMethod.GET, value = "/getAll", produces = "application/json;charset=UTF-8")
    public R getAll(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                    @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize) {
        //String openid = CheckLoginUtil.getOpenidMayNull(request, response);
        return shopService.getAll(pageNum,pageSize);
    }

    @Login
    @RequestMapping(method = RequestMethod.POST, value = "/getById", produces = "application/json;charset=UTF-8")
    public R getById(@RequestBody ShowProductForm param, @LoginUser UserEntity user) {

        //表单校验
        ValidatorUtils.validateEntity(param);
        Long userId = user.getUserId();

        return shopService.selectById(param.getId(),userId);
    }

    /**
     * 获得所有分类
     */
    @GetMapping("/getCategory")
    public R getCategory(){
        return shopService.selectCategory();
    }

    /**
     * 返回某个分类的所有商品
     */
    @PostMapping("/byCategory")
    public R byCategory(@RequestBody ShowProductForm form,
                        @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        //表单校验
        ValidatorUtils.validateEntity(form);

        return shopService.selectByCategory(form.getId(),pageNum,pageSize);
    }

    /**
     * 商品添加在购物车
     */
    @Login
    @RequestMapping(method = RequestMethod.POST, value = "/addCart", produces = "application/json;charset=UTF-8")
    public R addCart(@RequestBody ShowProductForm param, @LoginUser UserEntity user) {

        //表单校验
        ValidatorUtils.validateEntity(param);
        Long userId = user.getUserId();

        return shopService.add(userId,param);
    }

    /**
     * 购买购物车的商品
     */
    @Login
    @RequestMapping(method = RequestMethod.POST, value = "/buyCart", produces = "application/json;charset=UTF-8")
    public R buyCart(@RequestBody BuyShoppingCartForm param, @LoginUser UserEntity user) {

        Long userId = user.getUserId();

        return shopService.buy(userId,param);
    }

    /**
     * 删除购物车某个商品
     */
    @Login
    @RequestMapping(method = RequestMethod.POST, value = "/removeCart", produces = "application/json;charset=UTF-8")
    public R removeCart(@RequestBody ShowProductForm param, @LoginUser UserEntity user) {

        //表单校验
        ValidatorUtils.validateEntity(param);
        Long userId = user.getUserId();

        return shopService.remove(userId,param);
    }

    /**
     * 获取购物车内容
     */
    @Login
    @RequestMapping(method = RequestMethod.GET, value = "/getAllCart", produces = "application/json;charset=UTF-8")
    public R getAllCart(@LoginUser UserEntity user) {

        Long userId = user.getUserId();

        return shopService.selectAll(userId);
    }
}
