package com.smallbell.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "疲劳检测结果表单")
public class ResultForm {
    @ApiModelProperty(value = "uuid")
    @NotBlank(message="uuid不能为空")
    private String uuid;

    @ApiModelProperty(value = "预测结果")
    @NotBlank(message="密码不能为空")
    private String pred;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private Integer timestamp;

}

