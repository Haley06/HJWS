package com.smallbell.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smallbell.common.utils.R;
import com.smallbell.modules.app.entity.FatigueResultEntity;
import com.smallbell.modules.app.form.ResultForm;
import com.smallbell.modules.app.form.result.MoodForm;

import java.util.ArrayList;
import java.util.concurrent.Future;

public interface ResultService extends IService<FatigueResultEntity> {

    R setAlcohol(Long userId,Long time);

    R getAlcohol(Long userId,Long start,Long end);

    R getAllAlcohol(Long userId);

    R setMood(MoodForm form,Long userId);

    R getMood(Long userId);

    R getPeriodMood(Long userId,Long start,Long end);

    R getPeriodLookAhead(Long userId,Long start,Long end);

    R setLookAhead(Long userId,Long time);

    R getLookAhead(Long userId);

    Future<String[]> async(Long userId) throws InterruptedException;

    R beforeAsync(Long timestamp,Long userId);

    int addRecord(Long userId, ResultForm form);

    ArrayList<Integer> queryResult(Long userId, Long start, Long end);



}
