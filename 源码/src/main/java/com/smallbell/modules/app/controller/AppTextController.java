package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.PortsEntity;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.AddTextForm;
import com.smallbell.modules.app.form.UpdateTextForm;
import com.smallbell.modules.app.service.TextService;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/text")
public class AppTextController {

    @Resource
    private TextService textService;

    /**
     * 添加帖子
     */
    @Login
    @PostMapping("/add")
    public R addText(@RequestBody AddTextForm form, @LoginUser UserEntity user){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();

        return textService.addText(form,userId);

    }

    /**
     * 更新帖子
     */
    @Login
    @PostMapping("/update")
    public R updateText(@RequestBody UpdateTextForm form, @LoginUser UserEntity user){

        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();

        return textService.updateText(form,userId);
    }

    /**
     * 删除帖子
     */
    @GetMapping("/delete")
    public R deleteText(@RequestParam("id") int id){

        return textService.deleteText(id);
    }

    /**
     * 返回用户发表的帖子
     */
    @Login
    @GetMapping("/byUser")
    public R byUserText(@LoginUser UserEntity user,
                        @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){

        Long userId = user.getUserId();
        return textService.getByUser(userId,pageNum,pageSize);
    }

    /**
     * 返回所有帖子
     */
    @Login
    @GetMapping("/all")
    public R allText(@LoginUser UserEntity user,
                     @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                     @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    )
                     {

        return textService.getAll(user.getUserId(),pageNum,pageSize);
    }

    /**
     * 返回帖子详情
     */
    @Login
    @GetMapping("/info")
    public R infoText(@LoginUser UserEntity user,
                      @RequestParam("id") int id){
        return textService.infoText(user.getUserId(),id);
    }

}
