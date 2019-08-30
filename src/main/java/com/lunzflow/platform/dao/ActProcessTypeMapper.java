package com.lunzflow.platform.dao;

import java.util.List;

import com.lunzflow.platform.entity.ActProcessType;

public interface ActProcessTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActProcessType record);

    int insertSelective(ActProcessType record);

    ActProcessType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActProcessType record);

    int updateByPrimaryKey(ActProcessType record);
    List<ActProcessType> selectAll();
}