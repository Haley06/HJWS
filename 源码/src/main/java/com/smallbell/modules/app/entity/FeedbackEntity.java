package com.smallbell.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 反馈信息
 */
@Data
@TableName("tb_feedback")
public class FeedbackEntity implements Serializable {

    @TableId
    private Long id;

    private String type;

    private String detail;
}
