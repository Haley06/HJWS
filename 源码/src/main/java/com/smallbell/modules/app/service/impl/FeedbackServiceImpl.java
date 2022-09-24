package com.smallbell.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallbell.modules.app.dao.FatigueResultDao;
import com.smallbell.modules.app.dao.FeedbackDao;
import com.smallbell.modules.app.entity.FatigueResultEntity;
import com.smallbell.modules.app.entity.FeedbackEntity;
import com.smallbell.modules.app.form.FeedbackForm;
import com.smallbell.modules.app.form.FeedbackForms;
import com.smallbell.modules.app.service.FatigueResultService;
import com.smallbell.modules.app.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("feedbackService")
public class FeedbackServiceImpl extends ServiceImpl<FeedbackDao, FeedbackEntity>
        implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public void saveFeedbacks(FeedbackForms form) {
        List<FeedbackForm> info = form.getInfo();
        for (FeedbackForm f : info) {
            FeedbackEntity entity = new FeedbackEntity();
            entity.setType(f.getType());
            entity.setDetail(f.getDetail());
            feedbackDao.insert(entity);
        }
    }
}

