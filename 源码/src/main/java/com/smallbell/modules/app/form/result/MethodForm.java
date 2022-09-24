package com.smallbell.modules.app.form.result;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MethodForm {
    @NotNull(message = "开始时间不能为空")
    private Long start;
    @NotNull(message = "结束时间不能为空")
    private Long end;
    @NotNull(message = "类型不能为空")
    private Integer type;  //0为token，1为传入的userId

    private Long userId;
}
