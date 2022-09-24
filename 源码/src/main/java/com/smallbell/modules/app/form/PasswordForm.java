package com.smallbell.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改密码表单
 */
@Data
@ApiModel(value = "修改密码表单")
public class PasswordForm {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "原密码")
    private String password;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String newPassword;

}
