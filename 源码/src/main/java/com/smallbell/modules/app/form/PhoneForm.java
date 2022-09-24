package com.smallbell.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户联系人表单
 */
@Data
@ApiModel(value = "联系人表单")
public class PhoneForm {
    @ApiModelProperty(value = "主联系人")
    @NotBlank(message="主联系人不能为空")
    private String phone1;

    @ApiModelProperty(value = "联系人2")
    private String phone2;

    @ApiModelProperty(value = "联系人3")
    private String phone3;
}
