package com.smallbell.modules.app.form.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommentBo {
    @ApiModelProperty("评论id")
    private Integer commentId;

    @ApiModelProperty("评论者id")
    private Long userId;

    @ApiModelProperty("帖子id")
    private Integer recordId;

    @ApiModelProperty("评论内容共")
    private String commentBody;

    @ApiModelProperty("评论者头像")
    private String avatarUrl;

    @ApiModelProperty("评论者昵称")
    private String nickname;

    @ApiModelProperty("评论点赞数")
    private Integer favoriteNumber;

    @ApiModelProperty("评论时间")
    private Date commentTime;

    @ApiModelProperty("当前登录用户是否点赞该评论")
    private Integer isFavorite;

    private List<CommentBo> list;

    private String isDeleted;

    private String describe;  //帖子内容
}
