package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.common.utils.RedisUtils;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.CodeForm;
import com.smallbell.modules.app.form.LoginForm;
import com.smallbell.modules.app.form.PasswordForm;
import com.smallbell.modules.app.service.UserService;
import com.smallbell.modules.app.utils.JwtUtils;
import com.smallbell.modules.app.utils.SmsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app")
@Api("APP密码重置接口")
public class AppPasswordController {
    @Autowired
    private UserService userService;
    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("vercode")
    @ApiOperation("获取验证码（需要传入mobile手机号和uuid自定义的一个字符串）")
    public R verificationCode(@RequestParam("mobile") String mobile, @RequestParam("uuid") String uuid) {
        String code = smsUtils.generateCode();
        redisUtils.set(uuid + mobile, code,60*5);
        String testContent = "【护驾卫士】您的验证码是" + code + ",５分钟内有效。若非本人操作请忽略此消息。";
        //smsUtils.send(mobile, code);
        return R.ok(testContent);
    }

    @PostMapping("vercode")
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

    @Login
    @PostMapping("reset")
    @ApiOperation("重置密码(需要token,需要原密码)")
    public R resetPassword(@RequestBody PasswordForm form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        LoginForm loginForm = new LoginForm();
        loginForm.setMobile(form.getMobile());
        loginForm.setPassword(form.getPassword());
        long userId = userService.login(loginForm);
        //修改密码
        String result = userService.resetPassword(form.getMobile(), form.getNewPassword());
        return R.ok(result);
    }

    @Login
    @PostMapping("set")
    @ApiOperation("重置密码(需要token,不需要原密码，不需要query)")
    public R setPassword(@RequestBody PasswordForm form, @LoginUser UserEntity user) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        String mobile = user.getMobile();
        if (!mobile.equals(form.getMobile())) {
            return R.error("输入的手机号有误");
        }
        //修改密码
        String result = userService.resetPassword(mobile, form.getNewPassword());
        return R.ok(result);
    }
}
