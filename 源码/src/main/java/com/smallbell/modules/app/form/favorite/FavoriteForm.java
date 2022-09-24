package com.smallbell.modules.app.form.favorite;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FavoriteForm {
    @NotNull(message = "点赞类型不能为空")
    //@ApiModelProperty("0为对帖子点赞，1为对评论点赞")
    private Integer typeId;

    @NotNull(message = "标记字段不能为空")
    //@ApiModelProperty("typeId为0为帖子id，typeId为1为评论id")
    private Integer remark;
}
