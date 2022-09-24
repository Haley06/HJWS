package com.smallbell.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.smallbell.common.utils.PageUtils;
import com.smallbell.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 *
 *
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
