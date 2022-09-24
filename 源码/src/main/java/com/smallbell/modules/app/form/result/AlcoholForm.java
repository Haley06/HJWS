package com.smallbell.modules.app.form.result;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AlcoholForm {
    @NotNull(message = "time不能为空")
    private Long time;
}
