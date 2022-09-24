package com.smallbell.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "反馈完整表单")
public class FeedbackForms {

    @ApiModelProperty(value = "反馈列表")
    private List<FeedbackForm> info;

}