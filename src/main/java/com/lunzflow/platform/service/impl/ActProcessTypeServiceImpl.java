package com.lunzflow.platform.service.impl;

import java.util.List;

import com.lunzflow.platform.service.ActProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunzflow.platform.dao.ActProcessTypeMapper;
import com.lunzflow.platform.entity.ActProcessType;

@Service("actProcessTypeServiceImpl")
public class ActProcessTypeServiceImpl implements ActProcessTypeService {

	@Autowired
	ActProcessTypeMapper actProcessTypeMapper;
	@Override
	public List<ActProcessType> selectAll() {
		return actProcessTypeMapper.selectAll();
	}

	@Override
	public int insert(ActProcessType record) {
		
		return actProcessTypeMapper.insert(record);
	}
}
