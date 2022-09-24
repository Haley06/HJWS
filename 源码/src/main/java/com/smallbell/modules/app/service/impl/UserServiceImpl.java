package com.smallbell.modules.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallbell.common.exception.RRException;
import com.smallbell.common.validator.Assert;
import com.smallbell.modules.app.dao.UserDao;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.LoginForm;
import com.smallbell.modules.app.form.UsernameForm;
import com.smallbell.modules.app.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	@Override
	public UserEntity queryByMobile(String mobile) {
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("mobile", mobile));
	}

	@Override
	public long login(LoginForm form) {
		UserEntity user = queryByMobile(form.getMobile());
		Assert.isNull(user, "手机号或密码错误");

		System.out.println(DigestUtils.sha256Hex(form.getPassword()));

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new RRException("手机号或密码错误");
		}

		return user.getUserId();
	}

	@Override
	public long loginByUsername(UsernameForm form) {
		UserEntity user = baseMapper.selectOne(
				new QueryWrapper<UserEntity>()
					.eq("username", form.getUsername()));
		Assert.isNull(user, "手机号或密码错误");

		System.out.println(DigestUtils.sha256Hex(form.getPassword()));

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new RRException("手机号或密码错误");
		}

		return user.getUserId();
	}

	/**
	 * 根据手机号修改密码
	 * @param mobile
	 * @param newPassword
	 * @return
	 */
	@Override
	public String resetPassword(String mobile, String newPassword) {
		UserEntity userEntity = queryByMobile(mobile);
		userEntity.setPassword(DigestUtils.sha256Hex(newPassword));
		baseMapper.updateById(userEntity);
		return userEntity.getMobile();
	}

	@Override
	public Long getIdByMobile(String mobile) {
		UserEntity userEntity = queryByMobile(mobile);
		return userEntity.getUserId();
	}
}
