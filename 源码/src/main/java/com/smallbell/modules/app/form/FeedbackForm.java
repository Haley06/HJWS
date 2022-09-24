package com.smallbell.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "反馈表单")
public class FeedbackForm {
    @ApiModelProperty(value = "种类")
    private String type;

    @ApiModelProperty(value = "反馈内容")
    private String detail;

}

