package com.smallbell.modules.app.form.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddShoppingOrderParam {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "数量")
    private Integer amount;
    @ApiModelProperty(value = "地址id")
    private Integer addressId;
}
