package com.smallbell.modules.app.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MonitoreEntity {

    private Integer id;

    private Long sendId;

    private Date createTime;

    private Integer isAccepted;

    private Long monitoredId;

    private Integer isAllowed;
}
