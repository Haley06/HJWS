package com.smallbell.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.LoginForm;
import com.smallbell.modules.app.form.UsernameForm;

/**
 * 用户
 *
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	long login(LoginForm form);

	/**
	 * 用户名密码登录
	 * @param form
	 * @return
	 */
	long loginByUsername(UsernameForm form);

	/**
	 * 重置密码
	 * @param mobile 电话
	 * @param newPassword 新密码
	 * @return
	 */
	String resetPassword(String mobile, String newPassword);

    Long getIdByMobile(String mobile);
}
