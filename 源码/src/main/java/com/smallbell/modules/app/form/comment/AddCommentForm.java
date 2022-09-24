package com.smallbell.modules.app.form.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AddCommentForm {
    @ApiModelProperty("帖子id")
    @NotNull
    private Integer postId;

    @ApiModelProperty("评论内容")
    @NotBlank
    private String commentBody;

    @ApiModelProperty("评论时间")
    @NotNull
    private Date commentTime;

    private Integer commentId;
}
