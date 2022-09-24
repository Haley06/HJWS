package com.smallbell.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.entity.UserInfoEntity;
import com.smallbell.modules.app.form.PhoneForm;
import com.smallbell.modules.app.form.RoleForm;
import com.smallbell.modules.app.form.UserInfoForm;

public interface UserInfoService  extends IService<UserInfoEntity> {

    String setUserInfo(String mobile, UserInfoForm form);

    UserInfoEntity queryByMobile(String mobile);

    String setPhoneInfo(String mobile, PhoneForm form);

    void setRoleInfo(String mobile, RoleForm form);
}
