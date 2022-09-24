package com.smallbell.modules.app.form.comment;

import lombok.Data;

@Data
public class UserIdAndCommentIdPo {
    private Long userId;
    private Integer commentId;

    public UserIdAndCommentIdPo() {
    }

    public UserIdAndCommentIdPo(Long userId, Integer commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}
