package com.smallbell.modules.app.form.monitor;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MonitoredIdForm {
    @NotNull(message = "不能为空")
    private Long monitoredId;
}
