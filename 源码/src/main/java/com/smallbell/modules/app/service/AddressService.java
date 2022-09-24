package com.smallbell.modules.app.service;

import com.smallbell.common.utils.R;
import com.smallbell.modules.app.form.address.AddUserAddressListParam;
import com.smallbell.modules.app.form.address.RemoveUserAddressListParam;
import com.smallbell.modules.app.form.address.UpdateUserAddressListParam;

import java.util.List;

public interface AddressService {
    /**
     * 添加常用地址
     */
    R add(Long userId, AddUserAddressListParam param);

    /**
     * 更新常用地址
     */
    R update(Long userId, UpdateUserAddressListParam param);

    /**
     * 删除常用地址
     */
    R remove(Long userId, RemoveUserAddressListParam param);

    /**
     * 获取常用地址
     */
    R getAll(Long userId);
}
