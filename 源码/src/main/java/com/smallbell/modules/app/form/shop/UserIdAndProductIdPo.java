package com.smallbell.modules.app.form.shop;

import lombok.Data;

@Data
public class UserIdAndProductIdPo {

    private Long userId;
    private Integer productId;

    public UserIdAndProductIdPo(Long userId, Integer remark) {
        this.userId = userId;
        this.productId = remark;
    }
}
