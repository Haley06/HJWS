package com.smallbell.modules.app.dao;

import com.smallbell.modules.app.entity.MessageEntity;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.message.SenderIdAndAccepterIdPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageDao {

    //UserEntity selectBYPrimaryKey(Long id);

    int insertSelective(MessageEntity entity);

    List<MessageEntity> selectBySenderAndAccepter(Long sendId,Long acceptId);

    int updateByPrimaryKeySelective(MessageEntity entity);

    UserEntity selectByPrimaryKey(Long userId);

    List<MessageEntity> getSenderByUserId(Long userId);

    List<MessageEntity> getAccepterByUserId(Long userId);

    MessageEntity getNewestMessage(SenderIdAndAccepterIdPo po);
}
