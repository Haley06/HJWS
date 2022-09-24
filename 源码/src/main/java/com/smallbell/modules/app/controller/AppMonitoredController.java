package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.monitor.*;
import com.smallbell.modules.app.service.MonitoredService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/app/monitored")
public class AppMonitoredController {

    @Resource
    private MonitoredService monitoredService;

    /**
     * 通过手机号搜索
     */
    @Login
    @PostMapping("/selectByPhone")
    public R selectByPhone(@LoginUser UserEntity user, @RequestBody PhoneForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        return monitoredService.selectByPhone(form,userId);
    }

    /**
     * 添加关注
     */
    @Login
    @PostMapping("/add")
    public R addFocus(@LoginUser UserEntity user, @RequestBody MonitoredIdForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        return monitoredService.addFocus(form,userId);
    }

    /**
     * 是否接受
     */
    @Login
    @PostMapping("/isAccepted")
    public R isAccepted(@LoginUser UserEntity user, @RequestBody AcceptedForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        return monitoredService.isAccepted(form,userId);
    }

    /**
     * 用户关注列表
     */
    @Login
    @GetMapping("/user")
    public R userFocus(@LoginUser UserEntity user){

        Long userId = user.getUserId();
        return monitoredService.userFocus(userId);
    }

    /**
     * 存储驾驶状态
     */
    @Login
    @PostMapping("/isDriven")
    public R isDriven(@LoginUser UserEntity user, @RequestBody DriveForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        return monitoredService.isDriven(form,userId);
    }

    /**
     * 用户关注人的驾驶状态
     */
    @Login
    @GetMapping("/userDriven")
    public R userDriven(@LoginUser UserEntity user){

        Long userId = user.getUserId();
        return monitoredService.userDriven(userId);
    }

    @GetMapping("/async")
    public R async(){
        String msg =  null;
        Future<String> result = null;
        try{
            result = monitoredService.async();
            msg = result.get(10, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (result != null){
                result.cancel(true);
            }
        }
        return R.ok().put("async",msg);
    }

    //@Login
    @PostMapping("/isAllowed")
    public R isAllowed(@RequestBody AllowedForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        return monitoredService.isAllowed(form);
    }

    @PostMapping("/userAccepted")
    public R userAccepted(@RequestBody UserAcceptedForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        return monitoredService.userAccepted(form);
    }


}
