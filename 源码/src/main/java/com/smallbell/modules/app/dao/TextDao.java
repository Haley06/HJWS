package com.smallbell.modules.app.dao;

import com.smallbell.modules.app.entity.PortsEntity;
import com.smallbell.modules.app.result.GetTextResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface TextDao {

    /**
     * 添加帖子
     * @param portsEntity
     * @return
     */
    int addText(PortsEntity portsEntity);

    /**
     * 删除帖子byPrimaryKey
     * @param id
     * @return
     */
    int deleteText(int id);

    /**
     * 返回帖子详情
     * @param id 帖子主键id
     * @return
     */
    GetTextResult infoText(int id);

    /**
     * 更新帖子ByPrimaryKey
     * @param entity
     * @return
     */
    int updateText(PortsEntity entity);

    List<GetTextResult> getAll();

    List<GetTextResult> getByUser(Long userId);

    PortsEntity selectByPrimaryKey(Integer id);

}
