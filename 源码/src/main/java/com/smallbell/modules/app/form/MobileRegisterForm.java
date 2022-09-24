package com.smallbell.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 注册表单
 *
 */
@Data
@ApiModel(value = "手机号注册表单")
public class MobileRegisterForm {

    @ApiModelProperty(value = "uuid")
    @NotBlank(message="uuid不能为空")
    private String uuid;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message="用户名不能为空")
    private String username;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message="验证码不能为空")
    private String code;

}

