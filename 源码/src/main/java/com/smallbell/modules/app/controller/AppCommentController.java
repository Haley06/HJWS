package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.comment.AddCommentForm;
import com.smallbell.modules.app.form.comment.CommentIdForm;
import com.smallbell.modules.app.form.comment.RecordIdForm;
import com.smallbell.modules.app.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/comment")
public class AppCommentController {

    @Resource
    private CommentService commentService;

    /**
     * 添加评论
     */
    @Login
    @PostMapping("/add")
    public R addComment(@LoginUser UserEntity user, @RequestBody AddCommentForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        return commentService.addComment(form,userId);
    }

    /**
     * 删除评论
     */
    @Login
    @PostMapping("/delete")
    public R deleteComment(@LoginUser UserEntity user, @RequestBody CommentIdForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        return commentService.deleteComment(form,userId);
    }

    /**
     * 获得用户发表的评论
     */
    @Login
    @GetMapping("/user")
    public R getCommentByUser(@LoginUser UserEntity user){

        Long userId = user.getUserId();
        return commentService.getUserComment(userId);
    }

    /**
     * 获得某个帖子的评论
     */
   // @Login
    @PostMapping("/record")
    public R getByRecordId(@RequestBody RecordIdForm form){

        ValidatorUtils.validateEntity(form);

        //Long userId = user.getUserId();
        return commentService.GetByRecordId(form);
    }
}
