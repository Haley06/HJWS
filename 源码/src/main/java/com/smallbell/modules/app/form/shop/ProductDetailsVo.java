package com.smallbell.modules.app.form.shop;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailsVo implements Serializable {
    private Integer id;
    private String name;
    private String subhead;
    private String category;
    private String description;
    private Integer price;
    private String picture;
    private Integer isFavorite;
    private Integer isCollection;
    private Integer amount;
}
