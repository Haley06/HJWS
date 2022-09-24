package com.smallbell.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallbell.modules.app.dao.FatigueResultDao;
import com.smallbell.modules.app.dao.UserDao;
import com.smallbell.modules.app.entity.FatigueResultEntity;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.entity.UserInfoEntity;
import com.smallbell.modules.app.form.ResultForm;
import com.smallbell.modules.app.service.FatigueResultService;
import com.smallbell.modules.app.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fatigueResultService")
public class FatigueResultServiceImpl extends ServiceImpl<FatigueResultDao, FatigueResultEntity>
        implements FatigueResultService {

    @Override
    public int addRecord(Long userId, ResultForm form) {
        FatigueResultEntity entity  = new FatigueResultEntity();
        entity.setUserId(userId);
        entity.setTimestamp(form.getTimestamp());
        save(entity);
        return 1;
    }

    @Override
    public Integer queryResult(Long userId, Long start, Long end) {
        Integer result = baseMapper.selectCount(
                new QueryWrapper<FatigueResultEntity>().eq("user_id", userId)
                    .ge("timestamp", start)
                    .lt("timestamp", end)
        );
        return result;
    }

}
