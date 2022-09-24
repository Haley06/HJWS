package com.smallbell.modules.app.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GetTextResult {

    private int id;

    private String userId;

    private String username;

    private String avatarUrl;

    private Date createTime;

    private String description;

    private int commonNumber;

    private int favoriteNumber;

    private Integer isFavorite;
}
