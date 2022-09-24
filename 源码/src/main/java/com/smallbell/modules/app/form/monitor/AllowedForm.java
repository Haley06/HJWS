package com.smallbell.modules.app.form.monitor;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AllowedForm {
    //0为接受，1为拒绝
    @NotNull(message = "参数不能为空")
    private Integer isAllowed;

    @NotNull(message = "参数不能为空")
    private Integer id;
}
