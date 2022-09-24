package com.smallbell.modules.app.service;

import com.smallbell.common.utils.R;
import com.smallbell.modules.app.form.message.GetChatForm;

import java.util.Date;

public interface MessageService {

    R sendMessage(String content, Long senderId, Long accepterId,Date createTime);

    R getChat(GetChatForm form,Long userId);

    R getMessagePage(Long userId);
}
