package com.smallbell.modules.app.form.monitor;

import lombok.Data;

@Data
public class UserPhoneResult {
    private Long userId;
    /**
     * 用户名
     */
    private String username;

    private String introduction;

    /**
     * 头像
     */
    private String avatarUrl;

    private Integer isFocus;

}
