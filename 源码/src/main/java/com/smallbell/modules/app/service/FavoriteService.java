package com.smallbell.modules.app.service;

import com.smallbell.common.utils.R;
import com.smallbell.modules.app.form.favorite.FavoriteForm;

public interface FavoriteService {

    /**
     * 给帖子或者评论点赞
     * @param form 点赞标识
     * @return 无
     */
    R addFavorite(FavoriteForm form, Long userId);

    /**
     * 取消给帖子或评论点赞
     * @param form 点赞标识
     * @return 无
     */
    R deleteFavorite(FavoriteForm form, Long userId);

    /**
     * 获得用户所有收藏
     * @param userId
     * @return
     */
    R getAll(Long userId);

    R getAllFavorite(Long userId);
}
