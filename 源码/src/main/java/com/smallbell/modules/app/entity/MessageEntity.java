package com.smallbell.modules.app.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MessageEntity {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息发送者
     */
    private Long sendId;

    /**
     * 消息接收者
     */
    private Long acceptId;

    /**
     * 消息所处状态，正常消息为0或1，0表示接收者未读，1标识接收者已读
     * 对于官方通知，采用第一位存储已读未读状态，高位存储推荐的帖子id
     * 如205，为奇数说明已读，205>>1 为 102，说明推荐的帖子id为102.
     */
    private Integer type;

    /**
     * 帖子的创建时间
     */
    private Date createTime;
}
