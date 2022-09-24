package com.smallbell.modules.app.form.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MessageFormBo {
    @ApiModelProperty("聊天对象")
    private Long chatUserId;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("最新消息")
    private String latestMessage;

    @ApiModelProperty("最新消息时间")
    private Date latestTime;

    @ApiModelProperty("未读消息数")
    private Integer unRead;
}
