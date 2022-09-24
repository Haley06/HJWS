package com.smallbell.modules.app.entity;

import lombok.Data;

@Data
public class FavoriteEntity {
    /**
     * 主键
     */
    private Integer id;

    /**
     * user表主键
     */
    private Long userId;

    /**
     * 根据typeId存储帖子id或者评论id
     */
    private Integer remark;

    /**
     * 0为对帖子点赞，1为对评论点赞
     */
    private Integer typeId;
}
