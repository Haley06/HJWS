package com.smallbell.modules.app.entity;

import lombok.Data;

@Data
public class ProductEntity {

    private Integer id;
    private Integer categoryId;
    private String picture;
    private String name;
    private String description;
    private String subhead;
    private Integer price;
}
