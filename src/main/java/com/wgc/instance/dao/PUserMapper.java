package com.wgc.instance.dao;

import com.wgc.instance.entity.PUser;

import java.util.List;


public interface PUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PUser record);

    PUser selectByPrimaryKey(Integer id);

    List<PUser> selectAll();

    int updateByPrimaryKey(PUser record);
}