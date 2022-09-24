package com.smallbell.modules.app.form.address;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RemoveUserAddressListParam {
    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Integer id;
}
