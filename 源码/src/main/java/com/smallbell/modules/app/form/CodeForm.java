package com.smallbell.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 验证码表单
 */
@Data
@ApiModel(value = "验证码表单")
public class CodeForm {

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message="手机号码不能为空")
    private String mobile;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message="验证码不能为空")
    private String code;

    @ApiModelProperty(value = "uuid")
    @NotBlank(message="uuid不能为空")
    private String uuid;
}
