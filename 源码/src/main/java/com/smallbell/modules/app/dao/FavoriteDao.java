package com.smallbell.modules.app.dao;

import com.smallbell.modules.app.entity.FavoriteEntity;
import com.smallbell.modules.app.form.comment.UserIdAndCommentIdPo;
import com.smallbell.modules.app.form.favorite.UserIdAndRecordIdPo;
import com.smallbell.modules.app.form.shop.UserIdAndProductIdPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FavoriteDao {

    FavoriteEntity selectByUserIdAndCommentId(UserIdAndCommentIdPo po);

    FavoriteEntity selectByUserIdAndRecordId(UserIdAndRecordIdPo po);

    int insertSelective(FavoriteEntity favorite);

    int deleteByPrimaryKey(Integer id);

    FavoriteEntity selectByUserIdAndProductId(UserIdAndProductIdPo po);

    ArrayList<Integer> selectByUserIdAndTypeId(Long userId);

    FavoriteEntity selectIsCollection(UserIdAndProductIdPo po);

    ArrayList<Integer> selectAllFavorite(Long userId);
}
