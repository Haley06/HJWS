package com.smallbell.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallbell.modules.app.dao.UserInfoDao;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.entity.UserInfoEntity;
import com.smallbell.modules.app.form.PhoneForm;
import com.smallbell.modules.app.form.RoleForm;
import com.smallbell.modules.app.form.UserInfoForm;
import com.smallbell.modules.app.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userInfoService")
public class UserInfoServiceImpl  extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {

    @Override
    public UserInfoEntity queryByMobile(String mobile) {
        return baseMapper.selectOne(new QueryWrapper<UserInfoEntity>().eq("mobile", mobile));
    }

    @Override
    public String setPhoneInfo(String mobile, PhoneForm form) {
        UserInfoEntity userInfo = queryByMobile(mobile);
        if (userInfo == null) {
            userInfo = new UserInfoEntity();
            userInfo.setMobile(mobile);
            baseMapper.saveByMobile(userInfo);
        }
        userInfo.setPhone1(form.getPhone1());

        if (form.getPhone2() != null && !form.getPhone2().equals("")) {
            userInfo.setPhone2(form.getPhone2());
        }

        if (form.getPhone3() != null && !form.getPhone3().equals("")) {
            userInfo.setPhone3(form.getPhone3());
        }

        baseMapper.updateById(userInfo);
        return mobile;
    }

    @Override
    public void setRoleInfo(String mobile, RoleForm form) {
        UserInfoEntity userInfo = queryByMobile(mobile);
        userInfo.setRole(form.getRole());
        baseMapper.updateById(userInfo);
    }

    @Override
    public String setUserInfo(String mobile, UserInfoForm form) {
        UserInfoEntity userInfo = queryByMobile(mobile);
        if (userInfo == null) {
            userInfo = new UserInfoEntity();
            userInfo.setMobile(mobile);
            baseMapper.saveByMobile(userInfo);
        }
        userInfo.setUsername(form.getUsername());
        userInfo.setIntroduction(form.getIntroduction());
        userInfo.setSex(form.getSex());

        baseMapper.updateById(userInfo);
        return mobile;
    }
}
