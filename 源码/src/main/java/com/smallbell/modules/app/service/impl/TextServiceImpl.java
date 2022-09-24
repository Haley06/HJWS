package com.smallbell.modules.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.PageUtil;
import com.smallbell.common.utils.R;
import com.smallbell.modules.app.dao.FavoriteDao;
import com.smallbell.modules.app.dao.TextDao;
import com.smallbell.modules.app.entity.FavoriteEntity;
import com.smallbell.modules.app.entity.PortsEntity;
import com.smallbell.modules.app.form.AddTextForm;
import com.smallbell.modules.app.form.UpdateTextForm;
import com.smallbell.modules.app.form.favorite.UserIdAndRecordIdPo;
import com.smallbell.modules.app.result.GetTextResult;
import com.smallbell.modules.app.service.TextService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TextServiceImpl implements TextService {

    @Resource
    private TextDao textDao;

    @Resource
    private FavoriteDao favoriteDao;

    @Override
    public R addText(AddTextForm form,Long userId) {

        PortsEntity entity = creatRecord(form, userId);


        return R.ok().put("postsId",entity.getId());
    }

    private PortsEntity creatRecord(AddTextForm form, Long userId) {

        //封装更新
        PortsEntity ports = new PortsEntity();

        ports.setUserId(userId);
        ports.setDescription(form.getContent());

        ports.setCreateTime(new Date());

        textDao.addText(ports);

        return ports;
    }

    @Override
    public R deleteText(int id) {

        textDao.deleteText(id);

        return R.ok();
    }

    @Override
    public R infoText(Long userId,int id) {

        GetTextResult getTextResult = textDao.infoText(id);
        if (getTextResult==null)
            return R.error("无此帖子");

        UserIdAndRecordIdPo po = new UserIdAndRecordIdPo(userId,getTextResult.getId());
        FavoriteEntity favoriteEntity = favoriteDao.selectByUserIdAndRecordId(po);
        if (favoriteEntity==null)
            getTextResult.setIsFavorite(0);
        else
            getTextResult.setIsFavorite(1);
        //System.out.println(entity.toString());

        return R.ok().put("info",getTextResult);
    }

    @Override
    public R updateText(UpdateTextForm form,Long userId) {

        PortsEntity entity = new PortsEntity();
        entity.setUserId(userId);
        entity.setCreateTime(new Date());
        entity.setId(form.getId());
        entity.setDescription(form.getDescription());

        textDao.updateText(entity);

        return R.ok();
    }

    @Override
    public R getAll(Long userId,Integer pageNum, Integer pageSize) {

        //return null;
        //PageHelper.startPage(pageNum,pageSize);

        List<GetTextResult> all = textDao.getAll();

        if (all.size()==0)
            return R.error("请先发表帖子");

        for (GetTextResult getTextResult : all) {
            UserIdAndRecordIdPo po = new UserIdAndRecordIdPo(userId,getTextResult.getId());
            FavoriteEntity favoriteEntity = favoriteDao.selectByUserIdAndRecordId(po);
            if (favoriteEntity==null)
                getTextResult.setIsFavorite(0);
            else
                getTextResult.setIsFavorite(1);
        }

        int totalPage = PageUtil.totalPage(all.size(), pageSize);//总页数

        //System.out.println(totalPage);
        List<GetTextResult> page = new ArrayList<>();
        if (pageNum<=totalPage){
            page = CollUtil.page(pageNum, pageSize, all);
        }



        return R.ok().put("allText",page).put("totalPage",totalPage);
    }

    @Override
    public R getByUser(Long userId, Integer pageNum, Integer pageSize) {

        //PageHelper.startPage(pageNum,pageSize);


        List<GetTextResult> byUser = textDao.getByUser(userId);

        if (byUser.size()==0)
            return R.error("请先发表帖子");

        for (GetTextResult getTextResult : byUser) {
            UserIdAndRecordIdPo po = new UserIdAndRecordIdPo(userId,getTextResult.getId());
            FavoriteEntity favoriteEntity = favoriteDao.selectByUserIdAndRecordId(po);
            if (favoriteEntity==null)
                getTextResult.setIsFavorite(0);
            else
                getTextResult.setIsFavorite(1);
        }

        //System.out.println(byUser.size());
        int totalPage = PageUtil.totalPage(byUser.size(), pageSize);//总页数

        //System.out.println(totalPage);
        List<GetTextResult> page = new ArrayList<>();
        if (pageNum<=totalPage){
            page = CollUtil.page(pageNum, pageSize, byUser);
        }

        return R.ok().put("byUser",page).put("totalPage",totalPage);
        //return null;
    }
}
