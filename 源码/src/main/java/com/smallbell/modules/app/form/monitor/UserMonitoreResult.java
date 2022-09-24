package com.smallbell.modules.app.form.monitor;

import lombok.Data;

import java.util.Date;

@Data
public class UserMonitoreResult {

    private Long sendId;

    private Integer id;

    private Long monitoredId;

    private Date createTime;

    private String username;

    private String avatarUrl;
}
