package com.smallbell.modules.app.service.impl;

import com.smallbell.common.utils.R;
import com.smallbell.modules.app.dao.AddressDao;
import com.smallbell.modules.app.entity.UserAddressListEntity;
import com.smallbell.modules.app.form.address.AddUserAddressListParam;
import com.smallbell.modules.app.form.address.RemoveUserAddressListParam;
import com.smallbell.modules.app.form.address.UpdateUserAddressListParam;
import com.smallbell.modules.app.form.address.UserAddressListExample;
import com.smallbell.modules.app.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressDao addressDao;

    @Override
    public R add(Long userId, AddUserAddressListParam param) {
        String consignee = param.getConsignee();
        String phoneNumber = param.getPhoneNumber();
        String address = param.getAddress();
        String detailedAddress = param.getDetailedAddress();
        String areaCode = param.getAreaCode();

        UserAddressListExample userAddressListExample = new UserAddressListExample();
        UserAddressListEntity userAddressList = new UserAddressListEntity();
        userAddressListExample.createCriteria().andUserIdEqualTo(userId).andConsigneeEqualTo(consignee).andPhonenumberEqualTo(
                phoneNumber).andAddressEqualTo(address).andDetailedAddressEqualTo(detailedAddress).andAreaCodeEqualTo(address);
        List<UserAddressListEntity> userAddressLists = addressDao.selectByExample(userAddressListExample);
        if (userAddressLists.size()==1){
            userAddressList = userAddressLists.get(0);
            userAddressList.setIsOn(true);
            addressDao.updateByPrimaryKey(userAddressList);
        } else {
            userAddressList.setUserId(userId);
            userAddressList.setConsignee(consignee);
            userAddressList.setPhoneNumber(phoneNumber);
            userAddressList.setAddress(address);
            userAddressList.setDetailedAddress(detailedAddress);
            userAddressList.setAreaCode(areaCode);
            userAddressList.setIsOn(true);
            addressDao.insert(userAddressList);
        }
        return R.ok().put("addressId",userAddressList.getId());
    }

    @Override
    public R update(Long userId, UpdateUserAddressListParam param) {
        Integer id = param.getId();
        String consignee = param.getConsignee();
        String phoneNumber = param.getPhoneNumber();
        String address = param.getAddress();
        String detailedAddress = param.getDetailedAddress();
        String areaCode = param.getAreaCode();

        UserAddressListEntity userAddressList = addressDao.selectByPrimaryKey(id);
        if (userAddressList==null) return R.error();

        userAddressList.setId(id);
        userAddressList.setUserId(userId);
        userAddressList.setConsignee(consignee);
        userAddressList.setPhoneNumber(phoneNumber);
        userAddressList.setAddress(address);
        userAddressList.setDetailedAddress(detailedAddress);
        userAddressList.setAreaCode(areaCode);
        userAddressList.setIsOn(true);

        addressDao.updateByPrimaryKey(userAddressList);
        return R.ok().put("addressId",userAddressList.getId());
    }

    @Override
    public R remove(Long userId, RemoveUserAddressListParam param) {
        Integer id = param.getId();
        UserAddressListEntity userAddressList = addressDao.selectByPrimaryKey(id);
        if (userAddressList==null) return R.error();

        userAddressList.setIsOn(false);
        addressDao.updateByPrimaryKey(userAddressList);
        return R.ok();
    }

    @Override
    public R getAll(Long userId) {
        UserAddressListExample userAddressListExample = new UserAddressListExample();
        userAddressListExample.createCriteria().andUserIdEqualTo(userId).andIsonEqualTo(true);
        //PageHelper.startPage(pageNum,pageSize);
        List<UserAddressListEntity> userAddressLists = addressDao.selectByExample(userAddressListExample);

        int count = userAddressLists.size();
        if (count==0)
            return R.ok().put("address",null);

        return R.ok().put("total", count).put("address",userAddressLists);
    }
}
