package com.smallbell.modules.app.controller;


import com.smallbell.common.utils.R;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.FeedbackForm;
import com.smallbell.modules.app.form.FeedbackForms;
import com.smallbell.modules.app.form.UserInfoForm;
import com.smallbell.modules.app.service.FeedbackService;
import com.smallbell.modules.app.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * APP反馈信息
 */
@RestController
@RequestMapping("/app")
public class AppFeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("setfeedback")
    @ApiOperation("设置反馈信息")
    public R setFallback(@RequestBody FeedbackForms form) {
        feedbackService.saveFeedbacks(form);
        return R.ok().put("result", "ok");
    }



}
