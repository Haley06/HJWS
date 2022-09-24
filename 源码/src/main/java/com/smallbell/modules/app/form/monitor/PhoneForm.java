package com.smallbell.modules.app.form.monitor;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PhoneForm {

    @NotBlank(message = "phone不能为空")
    private String phone;
}
