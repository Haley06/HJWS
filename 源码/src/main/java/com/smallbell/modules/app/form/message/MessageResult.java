package com.smallbell.modules.app.form.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MessageResult {

    private Integer id;

    @ApiModelProperty("消息文本内容")
    private String content;

    @ApiModelProperty("消息是否已读")
    private Integer type;

    @ApiModelProperty("消息发送时间")
    private Date createTime;

    @ApiModelProperty("发送者id")
    private Long sendId;

    @ApiModelProperty("发送者昵称")
    private String senderNickname;

//    @ApiModelProperty("发送者头像")
    private String senderAvatar;

    @ApiModelProperty("接受者id")
    private Long acceptId;

    @ApiModelProperty("接受者昵称")
    private String accepterNickname;

//    @ApiModelProperty("接收者头像")
    private String accepterAvatar;

    @ApiModelProperty("当前登录用户是发送者还是接收者，0或1表示")
    private Integer remark;

    private Integer monitoredId;
}
