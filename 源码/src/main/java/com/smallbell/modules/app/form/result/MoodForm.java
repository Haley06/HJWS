package com.smallbell.modules.app.form.result;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MoodForm {

    @NotNull(message = "time不能为空")
    private Long time;
    @NotNull(message = "feeling不能为空")
    private Integer feeling;
}
