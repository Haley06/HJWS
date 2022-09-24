package com.smallbell.modules.app.controller;


import com.smallbell.common.utils.RedisUtils;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.MobileRegisterForm;
import com.smallbell.modules.app.service.UserService;
import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.form.RegisterForm;
import com.smallbell.modules.app.utils.SmsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

/**
 * 注册
 *
 *
 */
@RestController
@RequestMapping("/app")
@Api("APP注册接口")
public class AppRegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("register/old")
    @ApiOperation("旧的注册")
    public R registerOld(@RequestBody RegisterForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setUsername(form.getUsername());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreateTime(new Date());
        userService.save(user);

        return R.ok();
    }

    @GetMapping("register/vercode")
    @ApiOperation("获取验证码（需要传入mobile手机号和uuid自定义的一个字符串）")
    public R verificationCode(@RequestParam("mobile") String mobile, @RequestParam("uuid") String uuid) {
        String code = smsUtils.generateCode();
        redisUtils.set(uuid + mobile, code);
        smsUtils.send(mobile, code);
        return R.ok();
    }

    @PostMapping("register/mobile")
    @ApiOperation("通过验证码注册")
    public R registerByMobile(@RequestBody MobileRegisterForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);
        String code = redisUtils.get(form.getUuid() + form.getMobile());
        if (!code.equals(form.getCode())) {
            return R.error("验证码错误");
        }
        
        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setUsername(form.getUsername());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreateTime(new Date());
        userService.saveOrUpdate(user);

        return R.ok();
    }

}
