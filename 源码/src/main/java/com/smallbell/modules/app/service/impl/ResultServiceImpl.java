package com.smallbell.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallbell.common.utils.R;
import com.smallbell.modules.app.dao.FatigueResultDao;
import com.smallbell.modules.app.dao.ResultDao;
import com.smallbell.modules.app.entity.AlcoholEntity;
import com.smallbell.modules.app.entity.FatigueResultEntity;
import com.smallbell.modules.app.entity.LookAheadEntity;
import com.smallbell.modules.app.entity.MoodEntity;
import com.smallbell.modules.app.form.ResultForm;
import com.smallbell.modules.app.form.result.LookAheadVo;
import com.smallbell.modules.app.form.result.MoodForm;
import com.smallbell.modules.app.form.result.MoodResult;
import com.smallbell.modules.app.form.result.MoodVo;
import com.smallbell.modules.app.service.ResultService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.Future;

@Service
public class ResultServiceImpl extends ServiceImpl<FatigueResultDao, FatigueResultEntity> implements ResultService {

    @Autowired
    private ResultDao resultDao;

    String msg1 = null;
    String msg2 = null;
    String msg3 = null;
    String msg4 = null;

    @Override
    public int addRecord(Long userId, ResultForm form) {
        msg1 = userId + "=" + "1";
        FatigueResultEntity entity  = new FatigueResultEntity();
        entity.setUserId(userId);
        entity.setTimestamp(form.getTimestamp());
        save(entity);
        return 1;
    }

    @Override
    public ArrayList<Integer> queryResult(Long userId, Long start, Long end) {
        ArrayList<Integer> integers = new ArrayList<>();
        Long period = (end - start)/7;
        for (int i = 0; i < 7; i++) {
            Integer result = baseMapper.selectCount(
                    new QueryWrapper<FatigueResultEntity>().eq("user_id", userId)
                            .ge("timestamp", period*(i+1)+start)
                            .lt("timestamp", end)
            );
            integers.add(result);
        }

        return integers;
    }

    @Override
    public R setAlcohol(Long userId, Long time) {
        msg2 = userId + "=" + "1";
        AlcoholEntity entity = new AlcoholEntity();
        entity.setUserId(userId);
        entity.setCreateTime(time);
        resultDao.setAlcohol(entity);
        return R.ok();
    }

    @Override
    public R getAlcohol(Long userId, Long start, Long end) {

        Integer integer = resultDao.selectAlcohol(userId, start, end);
        if (integer==null)
            return R.ok().put("total",0);
        System.out.println(integer);
        return R.ok().put("total",integer);
    }

    @Override
    public R getAllAlcohol(Long userId) {
        Integer integer = resultDao.selectAllAlcohol(userId);
        if (integer==null)
            return R.ok().put("total",0);
        //System.out.println(integer);
        return R.ok().put("total",integer);
    }

    @Override
    public R setMood(MoodForm form, Long userId) {
        msg3 = userId + "=" + form.getFeeling();
        MoodEntity entity = new MoodEntity();
        entity.setUserId(userId);
        entity.setTimestamp(form.getTime());
        entity.setFeeling(form.getFeeling());
        resultDao.setMood(entity);
        return R.ok();
    }

    @Override
    public R getMood(Long userId) {

        MoodVo vo = new MoodVo();
        vo.setUserId(userId);
        Integer allMood = resultDao.getMood(vo);
        if (allMood == 0)
            return R.error("无心情数据");
        MoodResult moodResult = new MoodResult();
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMinimumFractionDigits(2);
        for (int i = 0; i < 5; i++) {
            vo.setFeeling(i);
            Integer oneMood = resultDao.getMood(vo);
            String format = instance.format((float) oneMood / (float) allMood * 100);
            switch (i){
                case 0:
                    moodResult.setHappy(format);
                    break;
                case 1:
                    moodResult.setSadness(format);
                    break;
                case 2:
                    moodResult.setAngry(format);
                    break;
                case 3:
                    moodResult.setNormal(format);
                    break;
                case 4:
                    moodResult.setSurprise(format);
                    break;
            }
        }

        return R.ok().put("mood",moodResult);
    }

    @Override
    public R getPeriodMood(Long userId, Long start, Long end) {
        Integer allowed = resultDao.isAllowed(userId);
        if (allowed.equals(1))
            return R.error("对方不允许向你展示驾驶状态");
        MoodVo vo = new MoodVo();
        vo.setUserId(userId);
        vo.setEnd(end);
        vo.setStart(start);
        Integer allMood = resultDao.getMood(vo);
        if (allMood == 0)
            return R.error("无心情数据");
        MoodResult moodResult = new MoodResult();
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMinimumFractionDigits(2);
        for (int i = 0; i < 5; i++) {
            vo.setFeeling(i);
            Integer oneMood = resultDao.getMood(vo);
            String format = instance.format((float) oneMood / (float) allMood * 100);
            switch (i){
                case 0:
                    moodResult.setHappy(format);
                    break;
                case 1:
                    moodResult.setSadness(format);
                    break;
                case 2:
                    moodResult.setAngry(format);
                    break;
                case 3:
                    moodResult.setNormal(format);
                    break;
                case 4:
                    moodResult.setSurprise(format);
                    break;
            }
        }

        return R.ok().put("mood",moodResult);
    }

    @Override
    public R getPeriodLookAhead(Long userId, Long start, Long end) {
        Integer allowed = resultDao.isAllowed(userId);
        if (allowed.equals(1))
            return R.error("对方不允许向你展示驾驶状态");
        LookAheadVo vo = new LookAheadVo();
        vo.setUserId(userId);
        vo.setEnd(end);
        vo.setStart(start);
        Integer integer = resultDao.selectLookAhead(vo);
        if (integer==null)
            return R.ok().put("total",0);
        //System.out.println(integer);
        return R.ok().put("total",integer);
    }

    @Override
    public R setLookAhead(Long userId, Long time) {
        msg4 = userId + "=" + "1";
        LookAheadEntity entity = new LookAheadEntity();
        entity.setUserId(userId);
        entity.setTimestamp(time);
        resultDao.setLookAhead(entity);
        return R.ok();
    }

    @Override
    public R getLookAhead(Long userId) {
        LookAheadVo vo = new LookAheadVo();
        vo.setUserId(userId);
        Integer integer = resultDao.selectLookAhead(vo);
        if (integer==null)
            return R.ok().put("total",0);
        //System.out.println(integer);
        return R.ok().put("total",integer);
    }

    @Override
    public Future<String[]> async(Long userId) throws InterruptedException {
        while (true){
            synchronized (this){
                String[] msg = new String[4];
                if ((msg1 != null) || (msg2 != null) || (msg3 != null) || (msg4 != null)){
                    if(msg1 != null){
                        String[] split = msg1.split("=");
                        if (split[0].equals(userId.toString())){
                            msg[0] = split[1];
                            msg1 = null;
                        }
                    }
                    if(msg2 != null){
                        String[] split = msg2.split("=");
                        if (split[0].equals(userId.toString())){
                            msg[1] = split[1];
                            msg2 = null;
                        }
                    }
                    if(msg3 != null){
                        String[] split = msg3.split("=");
                        if (split[0].equals(userId.toString())){
                            msg[2] = split[1];
                            msg3 = null;
                        }
                    }
                    if(msg4 != null){
                        String[] split = msg4.split("=");
                        if (split[0].equals(userId.toString())){
                            msg[3] = split[1];
                            msg4 = null;
                        }
                    }
                    return new AsyncResult(msg);
                }
                Thread.sleep(100);
            }
        }

    }

    @Override
    public R beforeAsync(Long timestamp, Long userId) {

        ArrayList<Integer> arr = new ArrayList<>();

        LookAheadVo lookAheadVo = new LookAheadVo();
        lookAheadVo.setStart(timestamp);
        lookAheadVo.setEnd(timestamp);
        lookAheadVo.setUserId(userId);
        Integer integer = resultDao.selectLookAhead(lookAheadVo);
        System.out.println(integer);
        if (integer!=0)
            arr.add(1);
        else
            arr.add(0);

        Integer integer1 = resultDao.selectAlcohol(userId, timestamp, timestamp);
        System.out.println(integer1);
        if (integer1!=0)
            arr.add(1);
        else
            arr.add(0);

        MoodEntity entity = resultDao.moodFeeling(userId, timestamp);
        arr.add(entity.getFeeling());

        return R.ok().put("result",arr);
    }
}
