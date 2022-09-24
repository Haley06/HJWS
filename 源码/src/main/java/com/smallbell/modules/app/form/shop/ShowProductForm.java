package com.smallbell.modules.app.form.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ShowProductForm {

    @NotNull(message = "不能为空")
    //@ApiModelProperty("分类id")
    private Integer id;
}
