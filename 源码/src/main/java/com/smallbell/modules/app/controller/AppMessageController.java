package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.message.GetChatForm;
import com.smallbell.modules.app.form.message.SendMessageForm;
import com.smallbell.modules.app.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/app/message")
public class AppMessageController {

    @Resource
    private MessageService messageService;

    /**
     * 发消息
     */
    @Login
    @PostMapping("/send")
    R sendMessage(@RequestBody SendMessageForm form, @LoginUser UserEntity user){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        if (userId.equals(form.getAccepterId()))
            return R.error("不能向自己发消息");

        return messageService.sendMessage(form.getContent(), userId, form.getAccepterId(),form.getCreateTime());
    }

    /**
     * 获得聊天列表
     */
    @Login
    @GetMapping("/form")
    R getMessagePage(@LoginUser UserEntity user){
        Long userId = user.getUserId();
        return messageService.getMessagePage(userId);
    }

    /**
     * 获得详细聊天
     */
    @Login
    @PostMapping("/chat")
    R getChat(@RequestBody GetChatForm form,@LoginUser UserEntity user){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();

        return messageService.getChat(form,userId);
    }


}
