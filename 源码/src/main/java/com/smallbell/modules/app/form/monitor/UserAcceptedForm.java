package com.smallbell.modules.app.form.monitor;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserAcceptedForm {
    @NotNull(message = "monitoredId不能为空")
    private Integer monitoredId;
}
