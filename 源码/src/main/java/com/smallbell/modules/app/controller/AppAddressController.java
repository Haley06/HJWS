package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.address.AddUserAddressListParam;
import com.smallbell.modules.app.form.address.RemoveUserAddressListParam;
import com.smallbell.modules.app.form.address.UpdateUserAddressListParam;
import com.smallbell.modules.app.form.comment.AddCommentForm;
import com.smallbell.modules.app.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/address")
public class AppAddressController {

    @Resource
    private AddressService addressService;

    @Login
    @PostMapping("/add")
    public R addAddress(@LoginUser UserEntity user, @RequestBody AddUserAddressListParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        return addressService.add(userId,form);
    }

    @Login
    @PostMapping("/update")
    public R updateAddress(@LoginUser UserEntity user, @RequestBody UpdateUserAddressListParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        return addressService.update(userId,form);
    }

    @Login
    @PostMapping("/remove")
    public R removeAddress(@LoginUser UserEntity user, @RequestBody RemoveUserAddressListParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        return addressService.remove(userId,form);
    }

    @Login
    @GetMapping("/getAll")
    public R getAllAddress(@LoginUser UserEntity user){

        Long userId = user.getUserId();
        return addressService.getAll(userId);
    }
}
