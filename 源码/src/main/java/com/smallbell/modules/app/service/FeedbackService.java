package com.smallbell.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smallbell.modules.app.entity.FeedbackEntity;
import com.smallbell.modules.app.form.FeedbackForms;

public interface FeedbackService extends IService<FeedbackEntity> {

    void saveFeedbacks(FeedbackForms form);
}
