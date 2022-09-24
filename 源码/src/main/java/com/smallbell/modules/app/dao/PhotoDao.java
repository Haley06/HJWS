package com.smallbell.modules.app.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhotoDao {

    int updatePhoto(String s,Long id);
}
