package com.smallbell.modules.app.service;

import com.smallbell.common.utils.R;
import com.smallbell.modules.app.form.monitor.*;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

public interface MonitoredService {

    /**
     * 添加关注
     * @param form
     * @param userId
     * @return
     */
    R addFocus(MonitoredIdForm form, Long userId);

    /**
     * 是否接受
     * @param form
     * @param userId
     * @return
     */
    R isAccepted(AcceptedForm form,Long userId);

    /**
     * 返回用户关注的所有人
     * @param userId
     * @return
     */
    R userFocus(Long userId);

    /**
     * 通过手机号搜索
     * @param phoneForm
     * @param userId
     * @return
     */
    R selectByPhone(PhoneForm phoneForm,Long userId);


    R isDriven(DriveForm form,Long userId);

    /**
     * 返回关注的人的驾驶状态
     * @param userId
     * @return
     */
    R userDriven(Long userId);

    /**
     * 是否允许展示历史记录，默认不允许
     * @param form
     * @return
     */
    R isAllowed(AllowedForm form);

    Future<String> async() throws InterruptedException;

    /**
     * 用户有没有点同意、忽略
     */
    R userAccepted(UserAcceptedForm form);
}
