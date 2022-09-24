package com.smallbell.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户
 */
@Data
@TableName("tb_user")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Long userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 头像
	 */
	private String avatarUrl;

	private Integer status;
	/**
	 * 不正常驾驶的时间
	 */
	private Date abnormalTime;
	/**
	 * 正常驾驶的时间
	 */
	private Date drivenTime;
}
