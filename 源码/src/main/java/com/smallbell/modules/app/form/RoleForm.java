package com.smallbell.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "vip信息表单")
public class RoleForm {

    @ApiModelProperty(value = "vip信息")
    @NotBlank(message="vip信息不能为空")
    private String role;

}