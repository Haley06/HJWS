package com.smallbell.modules.app.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CommentEntity {
    /**
     * 评论id
     */
    private Integer id;

    /**
     * user表主键
     */
    private Long userId;

    /**
     * 父评论id
     */
    private Integer fatherId;

    /**
     * 点赞数
     */
    private Integer favoriteNumber;

    /**
     * 评论数
     */
    private Integer commentNumber;

    /**
     * 帖子id
     */
    private Integer postsId;

    /**
     * 评论内容
     */
    private String commentBody;

    /**
     * 评论时间
     */
    private Date commentTime;

    /**
     * 是否被删除
     */
    private Integer isDeleted;
}
