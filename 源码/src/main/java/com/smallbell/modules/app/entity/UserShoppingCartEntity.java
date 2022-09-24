package com.smallbell.modules.app.entity;

import lombok.Data;

@Data
public class UserShoppingCartEntity {
    private Integer id;

    private Long userId;

    private Integer productId;

    private Integer amount;

}
