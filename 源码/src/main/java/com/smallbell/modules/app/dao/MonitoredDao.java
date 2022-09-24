package com.smallbell.modules.app.dao;

import com.smallbell.modules.app.entity.MonitoreEntity;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.monitor.UserDrivenResult;
import com.smallbell.modules.app.form.monitor.UserMonitoreResult;
import com.smallbell.modules.app.form.monitor.UserPhoneResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonitoredDao {

    List<UserPhoneResult> selectByPhone(String phone);

    int insertMonitred(MonitoreEntity entity);

    int updateMonitored(MonitoreEntity entity);

    List<UserMonitoreResult> selectUser(Long userId);

    int updateByUserId(UserEntity entity);

    List<UserDrivenResult> selectByUserId(Long userId);

    Integer userAccepted(Integer id);

}
