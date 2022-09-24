package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.entity.UserInfoEntity;
import com.smallbell.modules.app.form.LoginForm;
import com.smallbell.modules.app.form.PhoneForm;
import com.smallbell.modules.app.form.RoleForm;
import com.smallbell.modules.app.form.UserInfoForm;
import com.smallbell.modules.app.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * APP用户信息
 *
 */
@RestController
@RequestMapping("/app")
@Api("APP用户信息接口，都需要token，query都不需要")
public class AppInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Login
    @PostMapping("info")
    @ApiOperation("设置用户信息(不需要query)")
    public R setUserInfo(@RequestBody UserInfoForm form, @LoginUser UserEntity user) {
        // 表单校验
        ValidatorUtils.validateEntity(form);

        String mobile = userInfoService.setUserInfo(user.getMobile(), form);

        return R.ok().put("mobile", mobile);
    }

    @Login
    @GetMapping("info")
    @ApiOperation("查询用户信息(不需要query)")
    public R getUserInfo(@LoginUser UserEntity user) {
        UserInfoEntity userInfo = userInfoService.queryByMobile(user.getMobile());
        if (userInfo == null) {
            return R.error("没有查到用户信息");
        }
        UserInfoForm form = new UserInfoForm();
        form.setSex(userInfo.getSex());
        form.setIntroduction(userInfo.getIntroduction());
        form.setUsername(userInfo.getUsername());
        form.setRole(userInfo.getRole());
        form.setAvatarUrl(user.getAvatarUrl());

        return R.ok().put("userId", user.getUserId())
                .put("info", form);
    }

    @Login
    @PostMapping("phone")
    @ApiOperation("设置联系人信息(不需要query)")
    public R setPhoneInfo(@RequestBody PhoneForm form, @LoginUser UserEntity user) {
        // 表单校验
        ValidatorUtils.validateEntity(form);

        String mobile = userInfoService.setPhoneInfo(user.getMobile(), form);

        return R.ok().put("mobile", mobile);
    }

    @Login
    @GetMapping("phone")
    @ApiOperation("查询联系人信息(不需要query)")
    public R getPhoneInfo(@LoginUser UserEntity user) {
        UserInfoEntity userInfo = userInfoService.queryByMobile(user.getMobile());
        if (userInfo == null) {
            return R.error("没有查到用户信息");
        }
        PhoneForm phones = new PhoneForm();
        phones.setPhone1(userInfo.getPhone1());
        phones.setPhone2(userInfo.getPhone2());
        phones.setPhone3(userInfo.getPhone3());
        return R.ok().put("phones", phones);
    }

    @Login
    @PostMapping("role")
    @ApiOperation("设置vip信息(不需要query)")
    public R setRoleInfo(@RequestBody RoleForm form, @LoginUser UserEntity user) {
        // 表单校验
        ValidatorUtils.validateEntity(form);

        userInfoService.setRoleInfo(user.getMobile(), form);

        return R.ok();
    }

    @Login
    @GetMapping("role")
    @ApiOperation("查询vip信息")
    public R getRoleInfo(@LoginUser UserEntity user) {
        UserInfoEntity userInfo = userInfoService.queryByMobile(user.getMobile());
        if (userInfo == null) {
            return R.error("没有查到用户信息");
        }

        return R.ok().put("role", userInfo.getRole());
    }


}
