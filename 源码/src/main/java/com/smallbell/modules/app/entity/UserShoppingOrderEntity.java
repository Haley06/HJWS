package com.smallbell.modules.app.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class UserShoppingOrderEntity {
    private Integer id;

    private Long userId;

    private Integer addressId;

    private String name;

    private String subhead;

    private String category;

    private String description;

    private Integer price;

    private Integer amount;

    private Date updatetime;

    private String picture;

    private Integer productId;
}
