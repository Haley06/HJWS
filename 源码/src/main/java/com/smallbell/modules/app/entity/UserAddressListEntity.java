package com.smallbell.modules.app.entity;

import lombok.Data;

@Data
public class UserAddressListEntity {
    private Integer id;

    private Long userId;

    private String consignee;

    private String phoneNumber;

    private String address;

    private String detailedAddress;

    private Boolean isOn;

    private String areaCode;
}
