package com.smallbell.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smallbell.common.utils.PageUtils;
import com.smallbell.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 *
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
}
