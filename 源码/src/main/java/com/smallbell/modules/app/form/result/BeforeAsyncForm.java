package com.smallbell.modules.app.form.result;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BeforeAsyncForm {
    @NotNull(message = "userId不能为空")
    private Long userId;
    @NotNull(message = "timestamp不能为空")
    private Long timestamp;
}
