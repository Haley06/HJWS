package com.smallbell.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 用户疲劳记录
 */
@Data
@TableName("tb_fatigue_result")
public class FatigueResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long userId;

    /**
     * 时间戳
     */
    private Integer timestamp;
}
