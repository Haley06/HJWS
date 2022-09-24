package com.smallbell.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 */
@Data
@TableName("tb_user_info")
public class UserInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 手机号
     */
    @TableId
    private String mobile;
    /**
     * 三个紧急联系人
     */
    private String phone1;
    private String phone2;
    private String phone3;
    /**
     * 昵称
     */
    private String username;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * vip
     */
    private String role;

}
