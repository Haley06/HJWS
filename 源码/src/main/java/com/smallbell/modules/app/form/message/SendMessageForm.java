package com.smallbell.modules.app.form.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SendMessageForm {

    @NotBlank(message = "发送消息内容不能为空")
    //@ApiModelProperty("消息内容")
    private String content;

    @NotNull(message = "消息接收者不能为空")
    //@ApiModelProperty("接收者userId")
    private Long accepterId;

    @NotNull(message = "消息发送时间不能为空")
    //@ApiModelProperty("消息发送时间")
    private Date createTime;
}
