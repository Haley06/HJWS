package com.smallbell.modules.app.form.address;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserAddressListParam {
    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Integer id;
    @ApiModelProperty(value = "收件人")
    @NotNull(message = "收件人不能为空")
    private String consignee;
    @ApiModelProperty(value = "手机号")
    @NotNull(message = "手机号不能为空")
    private String phoneNumber;
    @ApiModelProperty(value = "地址")
    @NotNull(message = "地址不能为空")
    private String address;
    @ApiModelProperty(value = "详细地址")
    @NotNull(message = "详细地址不能为空")
    private String detailedAddress;
    @ApiModelProperty(value = "地址编码")
    @NotNull(message = "地址编码不能为空")
    private String areaCode;
}
