package com.smallbell.modules.app.controller;


import com.smallbell.common.utils.RedisUtils;
import com.smallbell.modules.app.form.CodeForm;
import com.smallbell.modules.app.form.UsernameForm;
import com.smallbell.modules.app.service.UserService;
import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.form.LoginForm;
import com.smallbell.modules.app.utils.JwtUtils;
import com.smallbell.modules.app.utils.SmsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * APP登录授权
 *
 */
@RestController
@RequestMapping("/app")
@Api("APP登录接口")
public class AppLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation("手机号密码登录")
    public R login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        long userId = userService.login(form);

        //生成token
        String token = jwtUtils.generateToken(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return R.ok(map);
    }

    @PostMapping("login/username")
    @ApiOperation("用户名密码登录")
    public R loginByUserName(@RequestBody UsernameForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        long userId = userService.loginByUsername(form);

        //生成token
        String token = jwtUtils.generateToken(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return R.ok(map);
    }


    @GetMapping("login/vercode")
    @ApiOperation("获取验证码（需要传入手机号和uuid字符串）")
    public R verificationCode(@RequestParam("mobile") String mobile, @RequestParam("uuid") String uuid) {
        String code = smsUtils.generateCode();
        redisUtils.set(uuid + mobile, code);
        smsUtils.send(mobile, code);
        return R.ok();
    }

    @PostMapping("login/vercode")
    @ApiOperation("检验验证码,如果正确则得到token")
    public R verificationCode(@RequestBody CodeForm codeForm) {
        // 表单验证
        ValidatorUtils.validateEntity(codeForm);
        String code = redisUtils.get(codeForm.getUuid() + codeForm.getMobile());
        System.out.println(code);
        if (!code.equals(codeForm.getCode())) {
            return R.error("验证码错误");
        }
        redisUtils.delete(codeForm.getUuid() + codeForm.getMobile());
        Long id = userService.getIdByMobile(codeForm.getMobile());
        String token = jwtUtils.generateToken(id);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return R.ok(map);
    }



}
