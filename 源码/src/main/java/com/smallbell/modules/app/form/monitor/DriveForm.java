package com.smallbell.modules.app.form.monitor;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DriveForm {

    //0为没有驾驶，1为正常驾驶，2为危险驾驶
    @NotNull(message = "不能为空")
    private Integer isDriven;
}
