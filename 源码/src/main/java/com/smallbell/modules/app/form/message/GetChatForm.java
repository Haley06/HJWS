package com.smallbell.modules.app.form.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetChatForm {
    @NotNull(message = "聊天对象id不能为空")
    //@ApiModelProperty("聊天对象userId")
    private Long chatUserId;
}
