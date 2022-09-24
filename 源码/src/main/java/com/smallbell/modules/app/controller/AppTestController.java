package com.smallbell.modules.app.controller;


import com.smallbell.common.utils.R;
import com.smallbell.common.utils.RedisUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * APP测试接口
 */
@RestController
@RequestMapping("/app")
@Api("APP测试接口")
public class AppTestController {

//    @Autowired
//    public SmsUtils smsUtils;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtils redisUtils;

    @Login
    @GetMapping("userInfo")
    @ApiOperation("获取用户信息(header需要token，不需要query)")
    public R userInfo(@LoginUser UserEntity user){
        return R.ok().put("user", user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID(header需要token，不需要query)")
    public R userInfo(@RequestAttribute("userId") Integer userId){
        return R.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public R notToken(){
        return R.ok().put("msg", "无需token也能访问。。。");
    }

    @GetMapping("testtoken")
    public R testToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMCIsImlhdCI6MTYxMjE4NTkzNSwiZXhwIjoxNjEyNzkwNzM1fQ.tufIuzEZ97P2qXALfknzu3Eq21i3QhUhsXMEG-Zf4wVrDe5zK1oQauIsJ_-3Kfm-MbFrCOMF8W1f_DKoqbfhpA";
        Claims claimByToken = jwtUtils.getClaimByToken(token);
        String subject = claimByToken.getSubject();
        return R.ok(subject);
    }

    @GetMapping("testredis")
    @ApiOperation("忽略Token的redis验证测试")
    public R testJedis() {
        redisUtils.set("1", "123");
        return R.ok().put("123", redisUtils.get("123"));
    }
}
