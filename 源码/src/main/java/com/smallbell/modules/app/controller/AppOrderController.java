package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.shop.AddShoppingOrderParam;
import com.smallbell.modules.app.form.shop.ShowProductForm;
import com.smallbell.modules.app.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/order")
public class AppOrderController {

    @Resource
    private OrderService orderService;

    @Login
    @RequestMapping(method = RequestMethod.POST, value = "/add", produces = "application/json;charset=UTF-8")
    public R addOrder(@RequestBody AddShoppingOrderParam param, @LoginUser UserEntity user) {

        //表单校验
        ValidatorUtils.validateEntity(param);
        Long userId = user.getUserId();

        return orderService.addOrder(userId,param);
    }

    @Login
    @RequestMapping(method = RequestMethod.GET, value = "/getAll", produces = "application/json;charset=UTF-8")
    public R getById(@LoginUser UserEntity user) {

        Long userId = user.getUserId();

        return orderService.getAllOrder(userId);
    }
}
