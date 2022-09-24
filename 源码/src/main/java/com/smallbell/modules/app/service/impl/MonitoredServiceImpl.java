package com.smallbell.modules.app.service.impl;

import com.smallbell.common.exception.RRException;
import com.smallbell.common.utils.R;
import com.smallbell.modules.app.dao.MessageDao;
import com.smallbell.modules.app.dao.MonitoredDao;
import com.smallbell.modules.app.entity.MessageEntity;
import com.smallbell.modules.app.entity.MonitoreEntity;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.monitor.*;
import com.smallbell.modules.app.service.MonitoredService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

@Service
public class MonitoredServiceImpl implements MonitoredService{

    private String msg = null;

    @Resource
    private MonitoredDao monitoredDao;

    @Resource
    private MessageDao messageDao;

    @Override
    public R selectByPhone(PhoneForm form, Long userId) {

        List<UserPhoneResult> userEntities = monitoredDao.selectByPhone(form.getPhone());

        if (userEntities.size()==0)
            throw new RRException("无此用户");
        List<UserMonitoreResult> userMonitoreResults = monitoredDao.selectUser(userId);
        for (UserPhoneResult userEntity : userEntities) {
            if (userEntity.getUserId().equals(userId))
                throw new RuntimeException("不能关注自己");
            userEntity.setIsFocus(0);
            Long userId1 = userEntity.getUserId();
            for (UserMonitoreResult userMonitoreResult : userMonitoreResults) {
                if (userMonitoreResult.getMonitoredId().equals(userId1)){
                    userEntity.setIsFocus(1);
                }
            }
        }

        return R.ok().put("list",userEntities);
    }

    @Override
    public synchronized R isDriven(DriveForm form, Long userId) {
        this.msg=userId+"="+ form.getIsDriven();
        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setStatus(form.getIsDriven());
        if (form.getIsDriven()==2) {
            user.setAbnormalTime(new Date());
            monitoredDao.updateByUserId(user);
            return R.ok();
        }
        if (form.getIsDriven()==1) {
            user.setDrivenTime(new Date());
            monitoredDao.updateByUserId(user);
            return R.ok();
        }

        monitoredDao.updateByUserId(user);

        return R.ok();
    }

    @Override
    public R userDriven(Long userId){

        List<UserDrivenResult> userDrivenResults = this.monitoredDao.selectByUserId(userId);
        if (userDrivenResults.size() == 0) {
            throw new RRException("请先关注用户");
        }else {
            Iterator var3 = userDrivenResults.iterator();

            while (var3.hasNext()) {
                UserDrivenResult userDrivenResult = (UserDrivenResult) var3.next();
//                userDrivenResult.setStatus(null);
                if (userDrivenResult.getAbnormalTime() == null) {
                    userDrivenResult.setIsAbnormal(0);
                } else {
                    if (userDrivenResult.getAbnormalTime().getTime() + 604800000L < System.currentTimeMillis()) {
                        userDrivenResult.setIsAbnormal(0);
                    }

                    userDrivenResult.setIsAbnormal(1);
                    userDrivenResult.setAbnormalTime(null);
                }
            }
        }
        return R.ok().put("drivenLists", userDrivenResults);
        }

    @Override
    public R isAllowed(AllowedForm form) {
        MonitoreEntity entity = new MonitoreEntity();
        entity.setId(form.getId());
        entity.setIsAllowed(form.getIsAllowed());
        monitoredDao.updateMonitored(entity);
        return R.ok();
    }

    @Override
    @Async
    public Future<String> async() throws InterruptedException {
        while (true){
            synchronized (this){
                if (msg != null){
                    String resultMsg = msg;
                    msg = null;
                    return new AsyncResult(resultMsg);
                }
            }
            Thread.sleep(100);
        }
    }

    @Override
    public R userAccepted(UserAcceptedForm form) {

        Integer integer = monitoredDao.userAccepted(form.getMonitoredId());

        return R.ok().put("isAccepted",integer);
    }

    @Override
    public R addFocus(MonitoredIdForm form, Long userId) {

        checkUser(userId);
        checkUser(form.getMonitoredId());

        if (Objects.equals(form.getMonitoredId(), userId))
            throw new  RRException("不能关注自己");

        MonitoreEntity monitoreEntity = new MonitoreEntity();
        monitoreEntity.setMonitoredId(form.getMonitoredId());
        monitoreEntity.setSendId(userId);
        monitoreEntity.setCreateTime(new Date());
        System.out.println(monitoreEntity);
        monitoredDao.insertMonitred(monitoreEntity);

//        //发消息
        UserEntity userEntity = messageDao.selectByPrimaryKey(userId);
        MessageEntity message = new MessageEntity();
        message.setAcceptId(form.getMonitoredId());
        message.setCreateTime(new Date());
        message.setSendId(userId);
        message.setType(monitoreEntity.getId()*10);
        String content = userEntity.getUsername()+"申请关注你";
        message.setContent(content);
        messageDao.insertSelective(message);

        return R.ok().put("monitoreId",monitoreEntity.getId());
    }

    private void checkUser(Long userId) {
        UserEntity user = messageDao.selectByPrimaryKey(userId);
        if (user == null) {
            throw new RRException("用户不存在");
        }
    }

    @Override
    public R isAccepted(AcceptedForm form,Long userId) {
            MonitoreEntity monitoreEntity = new MonitoreEntity();
            monitoreEntity.setIsAccepted(form.getIsAccepted());
            monitoreEntity.setId(form.getId());
            monitoredDao.updateMonitored(monitoreEntity);

        return R.ok();
    }

    @Override
    public R userFocus(Long userId) {

        List<UserMonitoreResult> userMonitoreResults = monitoredDao.selectUser(userId);
        if(userMonitoreResults.size()==0)
            throw  new RRException("关注人列表为0");

        return R.ok().put("results",userMonitoreResults);
    }
}
