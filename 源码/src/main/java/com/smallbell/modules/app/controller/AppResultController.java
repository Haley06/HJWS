package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.FatigueResultEntity;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.LoginForm;
import com.smallbell.modules.app.form.ResultForm;
import com.smallbell.modules.app.form.result.AlcoholForm;
import com.smallbell.modules.app.form.result.MethodForm;
import com.smallbell.modules.app.form.result.MoodForm;
import com.smallbell.modules.app.service.FatigueResultService;
import com.smallbell.modules.app.service.ResultService;
import com.smallbell.modules.app.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/app")
@Api("APP疲劳检测结果接口")
public class AppResultController {

    @Autowired
    private FatigueResultService resultService;

    @Autowired
    private ResultService service;

    /**
     * 19疲劳检测结果记录
     * @param form
     * @param user
     * @return
     */
    @Login
    @PostMapping("/fatigue/record")
    @ApiOperation("记录预测结果（不需要query，需要数据列表）")
    public R fatigueRecord(@RequestBody ResultForm form, @LoginUser UserEntity user){
        service.addRecord(user.getUserId(),form);
        return R.ok();
    }

    /**
     * 19疲劳检测历史返回
     * @return
     */
    @Login
    @PostMapping("/fatigue/query")
    @ApiOperation("查询预测结果数量（需要query的start和end，是起止时间戳）")
    public R fatigueQuery(@RequestBody MethodForm form, @LoginUser UserEntity user){

        //表单校验
        ValidatorUtils.validateEntity(form);

        ArrayList<Integer> arrayList = new ArrayList<>();

        if (form.getType().equals(0))
            arrayList = service.queryResult(user.getUserId(), form.getStart(), form.getEnd());
        else
            arrayList = service.queryResult(form.getUserId(), form.getStart(), form.getEnd());
        return R.ok().put("count", arrayList);
    }

    @Login
    @PostMapping("/fatigue/alcohol")
    @ApiOperation("记录酒精结果（不需要query，需要数据列表）")
    public R fatigueAlcohol(@RequestBody AlcoholForm form, @LoginUser UserEntity user){
        //表单校验
        ValidatorUtils.validateEntity(form);
        service.setAlcohol(user.getUserId(),form.getTime());
        return R.ok();
    }

    @Login
    @PostMapping("/fatigue/period/getAlcohol")
    @ApiOperation("查询最近一周酒精结果数量（需要query的start和end，是起止时间戳）")
    public R fatigueGetAlcohol(@RequestBody MethodForm form,@LoginUser UserEntity user){
        //表单校验
        ValidatorUtils.validateEntity(form);

        if (form.getType().equals(0))
            return service.getAlcohol(user.getUserId(),form.getStart(), form.getEnd());
        else
            return service.getAlcohol(form.getUserId(),form.getStart(), form.getEnd());
    }

    @Login
    @GetMapping("/fatigue/getAlcohol")
    @ApiOperation("查询此人所有酒精结果数量")
    public R fatigueGetAllAlcohol(@LoginUser UserEntity user){
        return service.getAllAlcohol(user.getUserId());
    }

    @Login
    @PostMapping("/fatigue/mood")
    @ApiOperation("记录心情结果（不需要query，需要数据列表）")
    public R setMood(@RequestBody MoodForm form, @LoginUser UserEntity user){

        //表单校验
        ValidatorUtils.validateEntity(form);

        return service.setMood(form, user.getUserId());
    }

    @Login
    @GetMapping("/fatigue/getMood")
    @ApiOperation("返回心情结果")
    public R getMood( @LoginUser UserEntity user){
        return service.getMood(user.getUserId());
    }

    @Login
    @PostMapping("/fatigue/period/mood")
    @ApiOperation("查询心情一段时间的结果（需要query的start和end，是起止时间戳）")
    public R periodMood(@RequestBody MethodForm form, @LoginUser UserEntity user){
        //表单校验
        ValidatorUtils.validateEntity(form);

        if (form.getType().equals(0))
            return service.getPeriodMood(user.getUserId(),form.getStart(), form.getEnd());
        else
            return service.getPeriodMood(form.getUserId(),form.getStart(), form.getEnd());
    }

    @Login
    @PostMapping("/fatigue/lookAhead")
    @ApiOperation("记录长时间不目视前方结果（不需要query，需要数据列表）")
    public R lookAhead(@RequestBody AlcoholForm form, @LoginUser UserEntity user){
        //表单校验
        ValidatorUtils.validateEntity(form);
        return service.setLookAhead(user.getUserId(),form.getTime());
    }

    @Login
    @GetMapping("/fatigue/getLook")
    @ApiOperation("返回长时间不目视前方结果")
    public R getLook(@LoginUser UserEntity user){
        return service.getLookAhead(user.getUserId());
    }

    @Login
    @PostMapping("/fatigue/period/lookAhead")
    @ApiOperation("查询不目视前方时间段的结果（需要query的start和end，是起止时间戳）")
    public R periodLookAhead(@RequestBody MethodForm form,@LoginUser UserEntity user){
        //表单校验
        ValidatorUtils.validateEntity(form);

        if (form.getType().equals(0))
            return service.getPeriodLookAhead(user.getUserId(),form.getStart(), form.getEnd());
        else
            return service.getPeriodLookAhead(form.getUserId(),form.getStart(), form.getEnd());
    }

    @Login
    @GetMapping("/fatigue/async")
    public R async(@LoginUser UserEntity user){
        String[] msg =  null;
        Future<String[]> result = null;
        try{
            result = service.async(user.getUserId());
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
}
