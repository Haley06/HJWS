package com.smallbell.modules.app.entity;

import lombok.Data;

@Data
public class MoodEntity {
    private Integer id;
    private Long userId;
    private Integer feeling;
    private Long timestamp;
}
