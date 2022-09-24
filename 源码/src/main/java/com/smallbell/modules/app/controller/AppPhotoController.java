package com.smallbell.modules.app.controller;

import com.smallbell.common.utils.R;
import com.smallbell.modules.app.annotation.Login;
import com.smallbell.modules.app.annotation.LoginUser;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/photo")
public class AppPhotoController {

    @Resource
    private PhotoService photoService;

    @Login
    @PostMapping("/upload")
    R upload(@RequestParam("file") MultipartFile file, @LoginUser UserEntity user){
        Long userId = user.getUserId();
        return photoService.photo(file,userId);
    }
}
