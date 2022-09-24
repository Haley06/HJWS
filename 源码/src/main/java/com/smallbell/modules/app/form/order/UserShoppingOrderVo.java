package com.smallbell.modules.app.form.order;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserShoppingOrderVo implements Serializable {
    private Integer id;
    private Long userId;
    private String consignee;
    private String phoneNumber;
    private String address;
    private String detailedAddress;
    private String name;
    private Integer productId;
    private String subhead;
    private String description;
    private Integer price;
    private String picture;
    private Integer amount;
}
