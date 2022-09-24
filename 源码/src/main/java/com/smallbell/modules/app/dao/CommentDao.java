package com.smallbell.modules.app.dao;

import com.smallbell.modules.app.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {

    int insertSelective(CommentEntity comment);

    CommentEntity selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    //int updateIsDeleted(Integer id);

    List<CommentEntity> selectByUserId(Long userId);

    List<CommentEntity> selectByRecordId(Integer recordId);

    int updateByPrimaryKeySelective(CommentEntity comment);

    List<CommentEntity> selectByFatherId(Integer fatherId);
}
