package com.smallbell.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smallbell.modules.app.entity.FatigueResultEntity;
import com.smallbell.modules.app.form.ResultForm;

public interface FatigueResultService extends IService<FatigueResultEntity> {

    int addRecord(Long userId, ResultForm form);

    Integer queryResult(Long userId, Long start, Long end);

}
