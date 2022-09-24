package com.smallbell.modules.app.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateTextForm {

    @NotNull(message = "id不能为空")
    private Integer id;

    @NotBlank(message = "description不能为空")
    private String description;
}
