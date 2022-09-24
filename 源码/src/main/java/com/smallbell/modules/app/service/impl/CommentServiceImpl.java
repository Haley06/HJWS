package com.smallbell.modules.app.service.impl;

import com.smallbell.common.exception.RRException;
import com.smallbell.common.utils.R;
import com.smallbell.modules.app.dao.CommentDao;
import com.smallbell.modules.app.dao.FavoriteDao;
import com.smallbell.modules.app.dao.MessageDao;
import com.smallbell.modules.app.dao.TextDao;
import com.smallbell.modules.app.entity.CommentEntity;
import com.smallbell.modules.app.entity.FavoriteEntity;
import com.smallbell.modules.app.entity.PortsEntity;
import com.smallbell.modules.app.entity.UserEntity;
import com.smallbell.modules.app.form.comment.*;
import com.smallbell.modules.app.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private MessageDao messageDao;

    @Resource
    private TextDao textDao;

    @Resource
    private CommentDao commentDao;

    @Resource
    private FavoriteDao favoriteDao;

    @Override
    public R addComment(AddCommentForm form, Long userId) {

        //获得登录用户
        //User user = getUser(openid);

        //构造评论
        CommentEntity comment = new CommentEntity();
        comment.setUserId(userId);
        comment.setCommentBody(form.getCommentBody());
        comment.setCommentTime(form.getCommentTime());

        if (form.getCommentId()!=null)
            comment.setFatherId(form.getCommentId());

        //获得帖子且确保帖子存在
        PortsEntity recordSimple = getRecord(form.getPostId());
        comment.setPostsId(form.getPostId());

        //插入评论
        commentDao.insertSelective(comment);

        //构造返回参数
//        CommentIdParam commentIdParam = new CommentIdParam();
//        commentIdParam.setCommentId(comment.getId());

        //更行帖子评论数量
        recordSimple.setCommonNumber(recordSimple.getCommonNumber()+1);
        textDao.updateText(recordSimple);

        return R.ok().put("commentId",comment.getId());
    }

    @Override
    public R deleteComment(CommentIdForm form, Long userId) {

        //获得登录用户
        //User user = getUser(openid);

        //获得评论
        CommentEntity comment = commentDao.selectByPrimaryKey(form.getCommentId());
        if(comment == null) {
            throw new RRException("评论未找到");
        }



        //构造返回类
        Integer recordId = comment.getPostsId();
        PortsEntity recordSimple = getRecord(recordId);

        Long userId1 = recordSimple.getUserId();

        //确保评论归属于当前登录用户或者帖子归属于当前用户，避免越权
        if(!comment.getUserId().equals(userId)) {
            throw new RRException("用户权限异常");
        }

        //删除评论
        List<CommentEntity> commentEntities = commentDao.selectByFatherId(comment.getId());
        if (commentEntities.size()!=0) {
            CommentEntity entity = new CommentEntity();
            entity.setIsDeleted(1);
            entity.setId(comment.getId());
            commentDao.updateByPrimaryKeySelective(entity);
        }else {
            commentDao.deleteByPrimaryKey(comment.getId());
        }
        //更行帖子评论数量
        recordSimple.setCommonNumber(recordSimple.getCommonNumber()-1);
        textDao.updateText(recordSimple);

        return R.ok();
        //return null;
    }

    @Override
    public R getUserComment(Long userId) {
        //User user = getUser(openid);

        //获得用户所有评论
        List<CommentEntity> comments = commentDao.selectByUserId(userId);

        //构造返回类
        return getCommentListResultCommonResult(comments);

    }

    @Override
    public R GetByRecordId(RecordIdForm form) {

        Integer recordId = form.getRecordId();

        //获得帖子所有评论
        List<CommentEntity> comments = commentDao.selectByRecordId(recordId);

        //构造返回类
        return getCommentListResultCommonResult(comments);
        //return null;
    }

    private R getCommentListResultCommonResult(List<CommentEntity> comments) {

        List<CommentBo> lists = new ArrayList<>();

        for (CommentEntity comment : comments) {
            if (comment.getFatherId() == 0) {
                CommentBo commentBo1= creatCommentBo(comment);

                List<CommentBo> list = new ArrayList<>();
                List<CommentEntity> commentEntities = commentDao.selectByFatherId(comment.getId());
                if (commentEntities.size()!=0) {
                    for (CommentEntity commentEntity : commentEntities) {
                        CommentBo commentBo = creatCommentBo(commentEntity);
                        list.add(commentBo);
                    }
                }

                commentBo1.setList(list);
                if (comment.getIsDeleted()==1){
                    commentBo1.setCommentId(null);
                    commentBo1.setUserId(null);
                    commentBo1.setRecordId(null);
                    commentBo1.setCommentBody("此评论已被删除");
                    //commentBo1.setAvatarUrl(null);
                    //commentBo1.setNickname(null);
                    //commentBo1.setCommentTime(null);
                    commentBo1.setFavoriteNumber(null);
                    commentBo1.setIsFavorite(null);
                    commentBo1.setIsDeleted(null);
                }
                lists.add(commentBo1);
            }


        }
        return R.ok().put("results", lists);
    }

    public CommentBo creatCommentBo(CommentEntity comment) {
        CommentBo bo = new CommentBo();

        Long userId = comment.getUserId();

        UserEntity user = messageDao.selectByPrimaryKey(userId);
        if(user == null) {
            throw new RRException("用户未找到");
        }

        PortsEntity record = getRecord(comment.getPostsId());
        PortsEntity entity = textDao.selectByPrimaryKey(comment.getPostsId());

        bo.setCommentId(comment.getId());
        bo.setUserId(user.getUserId());
        bo.setRecordId(comment.getPostsId());
        bo.setCommentBody(comment.getCommentBody());
        bo.setAvatarUrl(user.getAvatarUrl());
        bo.setNickname(user.getUsername());
        bo.setCommentTime(comment.getCommentTime());
        bo.setFavoriteNumber(comment.getFavoriteNumber());
        bo.setDescribe(record.getDescription());
        if (entity==null)
            bo.setDescribe("此帖子已被删除");
        //User user1 = userMapper.selectByOpenid(openid);

        UserIdAndCommentIdPo po = new UserIdAndCommentIdPo(userId, comment.getId());
        FavoriteEntity favorite = favoriteDao.selectByUserIdAndCommentId(po);
        if(favorite == null) {
            bo.setIsFavorite(0);
        }
        else {
            bo.setIsFavorite(1);
        }

        return bo;
    }

    /**
     * 获得帖子，没有就抛异常
     * @param recordId 帖子id
     * @return record
     */
    public PortsEntity getRecord(Integer recordId) {
        PortsEntity recordSimple = textDao.selectByPrimaryKey(recordId);
        if (recordSimple == null) {
            throw new RRException("没有这个帖子");
        }
        return recordSimple;
    }
}
