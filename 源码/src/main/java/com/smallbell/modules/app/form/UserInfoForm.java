package com.smallbell.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户信息表单
 */
@Data
@ApiModel(value = "用户信息表单")
public class UserInfoForm {
    @ApiModelProperty(value = "性别")
    @NotNull(message="性别不能为空")
    private Integer sex;

    @ApiModelProperty(value = "介绍")
    private String introduction;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message="用户名不能为空")
    private String username;

    @ApiModelProperty(value = "VIP信息")
    //@NotBlank(message="VIP信息不能为空")
    private String role;

    private String avatarUrl;
}
