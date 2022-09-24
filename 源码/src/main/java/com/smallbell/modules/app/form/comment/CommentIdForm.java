package com.smallbell.modules.app.form.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentIdForm {
    @NotNull(message = "评论标识不能为空")
    @ApiModelProperty("评论id")
    private Integer commentId;
}
