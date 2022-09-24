package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.common.validator.ValidatorUtils;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.comment.AddCommentForm;
import com.smallbell.modules.app.form.favorite.FavoriteForm;
import com.smallbell.modules.app.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/favorite")
public class AppFavoriteController {

    @Resource
    private FavoriteService favoriteService;

    @Login
    @PostMapping("/add")
    public R addFavorite(@LoginUser UserEntity user, @RequestBody FavoriteForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        return favoriteService.addFavorite(form,userId);
    }

    @Login
    @PostMapping("/delete")
    public R deleteFavorite(@LoginUser UserEntity user, @RequestBody FavoriteForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Long userId = user.getUserId();
        return favoriteService.deleteFavorite(form,userId);
    }

    /**
     * 返回所有收藏
     * @param user
     * @return
     */

    @Login
    @GetMapping("/getAll")
    public R getAll(@LoginUser UserEntity user){

        Long userId = user.getUserId();
        return favoriteService.getAll(userId);
    }

    /**
     * 返回所有点赞
     * @param user
     * @return
     */
    @Login
    @GetMapping("/getAllFavorite")
    public R getAllFavorite(@LoginUser UserEntity user){

        Long userId = user.getUserId();
        return favoriteService.getAllFavorite(userId);
    }
}
