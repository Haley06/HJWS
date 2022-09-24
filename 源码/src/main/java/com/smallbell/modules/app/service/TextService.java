package com.smallbell.modules.app.service;

import com.smallbell.common.utils.R;
import com.smallbell.modules.app.form.AddTextForm;
import com.smallbell.modules.app.form.UpdateTextForm;

public interface TextService {
    /**
     * 添加帖子
     */
    R addText(AddTextForm form,Long userId);

    /**
     * 删除帖子
     */
    R deleteText(int id);

    /**
     * 返回某个帖子详情
     */
    R infoText(Long userId,int id);

    /**
     * 更新帖子
     */
    R updateText(UpdateTextForm form,Long userId);

    /**
     * 分页返回所有帖子
     */
    R getAll(Long userId,Integer pageNum,Integer pageSize);

    /**
     * 返回某个人发表的帖子
     */
    R getByUser(Long userId,Integer pageNum,Integer pageSize);
}
