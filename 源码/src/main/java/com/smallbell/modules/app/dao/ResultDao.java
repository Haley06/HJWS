package com.smallbell.modules.app.dao;

import com.smallbell.modules.app.entity.AlcoholEntity;
import com.smallbell.modules.app.entity.LookAheadEntity;
import com.smallbell.modules.app.entity.MoodEntity;
import com.smallbell.modules.app.form.result.LookAheadVo;
import com.smallbell.modules.app.form.result.MoodVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResultDao {

    int setAlcohol(AlcoholEntity entity);

    Integer selectAlcohol(Long userId,Long start,Long end);

    Integer selectAllAlcohol(Long userId);

    int setMood(MoodEntity entity);

    int setLookAhead(LookAheadEntity entity);

    Integer selectLookAhead(LookAheadVo vo);

    Integer getMood(MoodVo vo);

    Integer isAllowed(Long userId);

    MoodEntity moodFeeling(Long userId,Long timestamp);

}
