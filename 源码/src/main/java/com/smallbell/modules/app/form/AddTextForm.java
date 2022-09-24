package com.smallbell.modules.app.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class AddTextForm {

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty("帖子文本内容")
    private String content;



}
