package com.smallbell.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PortsEntity {

    private int id;

    private Long userId;

    private Date createTime;

    private String description;

    private int commonNumber;

    private int favoriteNumber;
}
