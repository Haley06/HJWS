package com.smallbell.modules.app.form.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BuyShoppingCartForm {
    @ApiModelProperty(value = "ids")
    private Integer[] ids;
    @ApiModelProperty(value = "ids")
    private Integer[] amounts;
    @ApiModelProperty(value = "地址id")
    private Integer addressId;
}
