package com.smallbell.modules.app.form.monitor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class UserDrivenResult {
    private Integer id;

    private Long userId;

    private Long monitoredId;

    private String username;

    private String avatarUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date abnormalTime;

    private Date drivenTime;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer status;

    private Integer isAbnormal;
}
