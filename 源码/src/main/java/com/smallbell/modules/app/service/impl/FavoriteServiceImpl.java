package com.smallbell.modules.app.service.impl;

import com.smallbell.common.exception.RRException;
import com.smallbell.common.utils.R;
import com.smallbell.modules.app.dao.CommentDao;
import com.smallbell.modules.app.dao.FavoriteDao;
import com.smallbell.modules.app.dao.ShopDao;
import com.smallbell.modules.app.dao.TextDao;
import com.smallbell.modules.app.entity.CommentEntity;
import com.smallbell.modules.app.entity.FavoriteEntity;
import com.smallbell.modules.app.entity.PortsEntity;
import com.smallbell.modules.app.entity.ProductEntity;
import com.smallbell.modules.app.form.comment.UserIdAndCommentIdPo;
import com.smallbell.modules.app.form.favorite.FavoriteForm;
import com.smallbell.modules.app.form.favorite.UserIdAndRecordIdPo;
import com.smallbell.modules.app.form.shop.ProductDetailsVo;
import com.smallbell.modules.app.form.shop.UserIdAndProductIdPo;
import com.smallbell.modules.app.service.FavoriteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Resource
    private CommentDao commentDao;

    @Resource
    private ShopDao shopDao;


    @Resource
    private TextDao textDao;

    @Resource
    private FavoriteDao favoriteDao;

    @Override
    public R addFavorite(FavoriteForm form, Long userId) {
        //获得用户
       // User user = getUser(openid);

        Integer remark = form.getRemark();

        //如果是对帖子点赞
        if (form.getTypeId().equals(0)) {
            //拿到帖子
            PortsEntity recordSimple = getRecord(remark);

            UserIdAndRecordIdPo po = new UserIdAndRecordIdPo(userId, remark);

            //查看是否已经点赞
            FavoriteEntity favorite = favoriteDao.selectByUserIdAndRecordId(po);
            if (favorite != null) {
                throw new RRException("请勿重复点赞");
            }

            //更行帖子点赞数量
            recordSimple.setFavoriteNumber(recordSimple.getFavoriteNumber() + 1);
            textDao.updateText(recordSimple);
        } else if (form.getTypeId().equals(1)) {
            //如果是对评论点赞
            CommentEntity comment = getComment(remark);

            UserIdAndCommentIdPo po = new UserIdAndCommentIdPo(userId, remark);

            //查看是否已经点赞
            FavoriteEntity favorite = favoriteDao.selectByUserIdAndCommentId(po);
            if (favorite != null) {
                throw new RRException("请勿重复点赞");
            }

            //更新帖子点赞数量
            comment.setFavoriteNumber(comment.getFavoriteNumber() + 1);
            commentDao.updateByPrimaryKeySelective(comment);
        }else if (form.getTypeId().equals(2)) {
            //如果是对商品点赞
            getProduct(remark);

            UserIdAndProductIdPo po = new UserIdAndProductIdPo(userId, remark);

            //查看是否已经点赞
            FavoriteEntity favorite = favoriteDao.selectByUserIdAndProductId(po);
            if (favorite != null) {
                throw new RRException("请勿重复点赞");
            }

        }else if (form.getTypeId().equals(3)) {
            //如果是对商品收藏
            getProduct(remark);

            UserIdAndProductIdPo po = new UserIdAndProductIdPo(userId, remark);

            //查看是否已经点赞
            FavoriteEntity favorite = favoriteDao.selectIsCollection(po);
            if (favorite != null) {
                throw new RRException("请勿重复点赞");
            }
        } else {
            //如果都不是就抛异常
            throw new RRException("点赞类型错误");
        }


        //构造点赞
        FavoriteEntity result = new FavoriteEntity();
        result.setUserId(userId);
        result.setRemark(remark);
        result.setTypeId(form.getTypeId());

        //插入
        favoriteDao.insertSelective(result);

        return R.ok();
    }

    public PortsEntity getRecord(Integer recordId) {
        PortsEntity recordSimple = textDao.selectByPrimaryKey(recordId);
        if (recordSimple == null) {
            throw new RRException("没有这个帖子");
        }
        return recordSimple;
    }

    private CommentEntity getComment(Integer commentId) {
        CommentEntity comment = commentDao.selectByPrimaryKey(commentId);
        if (comment == null) {
            throw new RRException("评论未找到");
        }
        return comment;
    }

    private void getProduct(Integer commentId) {
        ProductEntity product = shopDao.selectByPrimaryKey(commentId);
        if (product == null) {
            throw new RRException("商品未找到");
        }
    }

    @Override
    public R deleteFavorite(FavoriteForm form, Long userId) {
        //User user = getUser(openid);
        Integer remark = form.getRemark();
        Integer favoriteId;

        //如果是删除帖子的点赞
        if (form.getTypeId().equals(0)) {
            PortsEntity recordSimple = getRecord(remark);

            UserIdAndRecordIdPo po = new UserIdAndRecordIdPo(userId, remark);

            //判断点赞是否存在
            FavoriteEntity favorite = favoriteDao.selectByUserIdAndRecordId(po);
            if (favorite == null) {
                throw new RRException("用户尚未点赞");
            }
            favoriteId = favorite.getId();

            //更新帖子点赞数
            recordSimple.setFavoriteNumber(recordSimple.getFavoriteNumber() - 1);
            textDao.updateText(recordSimple);
        } else if (form.getTypeId().equals(1)) {
            //如果删除评论的点赞
            CommentEntity comment = getComment(remark);

            UserIdAndCommentIdPo po = new UserIdAndCommentIdPo(userId, remark);

            //判断点赞是否存在
            FavoriteEntity favorite = favoriteDao.selectByUserIdAndCommentId(po);
            if (favorite == null) {
                throw new RRException("用户尚未点赞");
            }
            favoriteId = favorite.getId();

            //更新评论点赞数
            comment.setFavoriteNumber(comment.getFavoriteNumber()-1);
            commentDao.updateByPrimaryKeySelective(comment);
        }else if (form.getTypeId().equals(2)) {
            //如果删除商品的点赞
            getProduct(remark);

            UserIdAndProductIdPo po = new UserIdAndProductIdPo(userId, remark);

            //判断点赞是否存在
            FavoriteEntity favorite = favoriteDao.selectByUserIdAndProductId(po);
            if (favorite == null) {
                throw new RRException("用户尚未点赞");
            }
            favoriteId = favorite.getId();

        }else if (form.getTypeId().equals(3)) {
            //如果删除商品的收藏
            getProduct(remark);

            UserIdAndProductIdPo po = new UserIdAndProductIdPo(userId, remark);

            //判断收藏是否存在
            FavoriteEntity favorite = favoriteDao.selectIsCollection(po);
            if (favorite == null) {
                throw new RRException("用户尚未点赞");
            }
            favoriteId = favorite.getId();

        } else {
            //都不是的话，就抛异常
            throw new RRException("点赞类型错误");
        }

        favoriteDao.deleteByPrimaryKey(favoriteId);
        return R.ok();
    }

    @Override
    public R getAll(Long userId) {

        ArrayList<Integer> arrayList = favoriteDao.selectByUserIdAndTypeId(userId);
        if (arrayList.size()==0)
            throw new RRException("用户尚未收藏商品");

        List<ProductDetailsVo> productDetailsVos = new ArrayList<>();
        for (Integer integer : arrayList) {
            ProductDetailsVo productDetailsVo = shopDao.selectById(integer);
            productDetailsVos.add(productDetailsVo);
        }
        return R.ok().put("all",productDetailsVos);
    }

    @Override
    public R getAllFavorite(Long userId) {

        ArrayList<Integer> arrayList = favoriteDao.selectAllFavorite(userId);
        if (arrayList.size()==0)
            throw new RRException("用户尚未点赞商品");

        List<ProductDetailsVo> productDetailsVos = new ArrayList<>();
        for (Integer integer : arrayList) {
            ProductDetailsVo productDetailsVo = shopDao.selectById(integer);
            productDetailsVos.add(productDetailsVo);
        }
        return R.ok().put("all",productDetailsVos);
    }
}
