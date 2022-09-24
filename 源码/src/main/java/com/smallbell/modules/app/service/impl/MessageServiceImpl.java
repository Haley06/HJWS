package com.smallbell.modules.app.service.impl;

import com.smallbell.common.exception.RRException;
import com.smallbell.common.utils.R;
import com.smallbell.modules.app.dao.MessageDao;
import com.smallbell.modules.app.entity.MessageEntity;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.message.GetChatForm;
import com.smallbell.modules.app.form.message.MessageFormBo;
import com.smallbell.modules.app.form.message.MessageResult;
import com.smallbell.modules.app.form.message.SenderIdAndAccepterIdPo;
import com.smallbell.modules.app.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageDao messageDao;

    @Override
    public R sendMessage(String content, Long senderId, Long accepterId,Date createTime) {
        //确定发送者与接收者都存在，否则抛出异常
        checkUser(senderId);
        checkUser(accepterId);

        //封装message
        MessageEntity message = new MessageEntity();

        message.setContent(content);
        message.setAcceptId(accepterId);
        message.setSendId(senderId);
        message.setCreateTime(createTime);

        //插入
        messageDao.insertSelective(message);

       // return message.getId();
        return R.ok().put("messageId",message.getId());
    }

    @Override
    public R getChat(GetChatForm form, Long userId) {

        Long chatUserId = form.getChatUserId();

        //获得登录用户
        UserEntity user = getUser(userId);

        //获得对话用户
        UserEntity chatUser = getUser(chatUserId);

        //封装po
//        SenderIdAndAccepterIdPo po = new SenderIdAndAccepterIdPo();
//        po.setAccepterId(user.getId());
//        po.setSenderId(chatUserId);

        //获得二者所有消息
        List<MessageEntity> messages = messageDao.selectBySenderAndAccepter(chatUserId,userId);

        List<MessageResult> results = new ArrayList<>();

        //如果消息未读，将消息设置为已读，添加remark字段标识用户为消息的接受者
        if(!messages.isEmpty()) {
            for (MessageEntity message : messages) {

                if(message.getType()%2 == 0) {
                    message.setType(message.getType() + 1);
                }
                messageDao.updateByPrimaryKeySelective(message);
                MessageResult result = creatResult(message, chatUser, user, 1);
                results.add(result);
            }
        }

        //封装po
//        po.setSenderId(user.getId());
//        po.setAccepterId(chatUserId);

        List<MessageEntity> messageList = messageDao.selectBySenderAndAccepter(userId,chatUserId);

        //添加remark字段标识当前登录用户为消息发送者
        if(!messageList.isEmpty()) {
            for (MessageEntity message : messageList) {
                MessageResult result = creatResult(message, user, chatUser, 0);
                results.add(result);
            }
        }

        //根据消息发送时间倒序排列
        results.sort(Comparator.comparing(MessageResult::getCreateTime));

        //封装返回类
//        GetChatResult result = new GetChatResult();
//        result.setMessageList(results);

 //       List<MessageResult> messageLists = new ArrayList<>();

        return R.ok().put("result",results);

        //return null;
    }

    @Override
    public R getMessagePage(Long userId) {

        UserEntity user = getUser(userId);

        Integer unRead = 0;
        //拿到用户接受的消息
        List<MessageEntity> senderByUserId = messageDao.getSenderByUserId(userId);

        HashMap<Long, Integer> userIdMap = new HashMap<>();

        //将相关用户的userId放入map中，并记录未读消息的数量
        for (MessageEntity message : senderByUserId) {
            userIdMap.putIfAbsent(message.getSendId(), 0);
            if(message.getType()%2 == 0) {
                Integer integer = userIdMap.get(message.getSendId());
                userIdMap.put(message.getSendId(), integer+1);
                unRead++;
            }
        }

        //拿到用户发送的消息
        List<MessageEntity> accepterByUserId = messageDao.getAccepterByUserId(userId);

        //将相关用户的userId放入map中
        for (MessageEntity message : accepterByUserId) {
            userIdMap.putIfAbsent(message.getAcceptId(), 0);
        }

        List<MessageFormBo> results = new ArrayList<>();

        //获得set集合
        Set<Long> userIdSet = userIdMap.keySet();

        //遍历set
        for (Long integer : userIdSet) {
            //构造po
            SenderIdAndAccepterIdPo po = new SenderIdAndAccepterIdPo();
            po.setAccepterId(user.getUserId());
            po.setSenderId(integer);

            //获得最新消息
            MessageEntity newestMessage = messageDao.getNewestMessage(po);

            //设置返回类
            MessageFormBo bo = new MessageFormBo();
            bo.setLatestMessage(newestMessage.getContent());
            bo.setLatestTime(newestMessage.getCreateTime());

            //根据当前用户是发送者还是接收者分别设置返回类
            if(newestMessage.getSendId().equals(userId)) {
                Long accepterId = newestMessage.getAcceptId();

                UserEntity user1 = getUser(accepterId);
                System.out.println(user1.toString());
                bo.setChatUserId(user1.getUserId());
                bo.setAvatar(user1.getAvatarUrl());
                bo.setNickname(user1.getUsername());
            }
            else {
                Long senderId = newestMessage.getSendId();

                UserEntity user1 = getUser(senderId);
                System.out.println(user1.toString());
                bo.setChatUserId(user1.getUserId());
                bo.setAvatar(user1.getAvatarUrl());
                bo.setNickname(user1.getUsername());
            }

            //设置未读数量
            bo.setUnRead(userIdMap.get(bo.getChatUserId()));
            results.add(bo);
        }

        //优先展示未读消息，其次根据时间排序
        results.sort(this::compare);

        //封装返回类
//        MessageFormResult result = new MessageFormResult();
//        result.setMessageFormList(results);
//        result.setUnRead(unRead);

        //return CommonResult.success(result);

        return R.ok().put("results",results).put("unRead",unRead);
    }

    public int compare(MessageFormBo a, MessageFormBo b) {
        if(a.getUnRead()>0 && b.getUnRead()==0) {
            return -1;
        }
        else if(a.getUnRead()==0 && b.getUnRead()==0) {
            return b.getLatestTime().compareTo(a.getLatestTime());
        }
        else if(a.getUnRead()==0 && b.getUnRead()>0) {
            return 1;
        }
        else {
            return b.getLatestTime().compareTo(a.getLatestTime());
        }
    }

    private void checkUser(Long userId) {
        UserEntity user = messageDao.selectByPrimaryKey(userId);
        if (user == null) {
            throw new RRException("用户不存在");
        }
    }


    private MessageResult creatResult(MessageEntity message, UserEntity sender, UserEntity accepter, Integer remark) {
        MessageResult result = new MessageResult();
        result.setId(message.getId());
        result.setContent(message.getContent());
        result.setType(message.getType());

        if (message.getType()/10!=0){
            result.setMonitoredId(message.getType()/10);
        }
        result.setCreateTime(message.getCreateTime());
        result.setSendId(sender.getUserId());
        result.setSenderNickname(sender.getUsername());
        result.setSenderAvatar(sender.getAvatarUrl());
        result.setAcceptId(accepter.getUserId());
        result.setAccepterAvatar(accepter.getAvatarUrl());
        result.setAccepterNickname(accepter.getUsername());

        result.setRemark(remark);

        return result;
    }

    private UserEntity getUser(Long userId) {
        UserEntity user = messageDao.selectByPrimaryKey(userId);
        if (user == null) {
            throw new RRException("用户不存在");
        }
        return user;
    }


}
