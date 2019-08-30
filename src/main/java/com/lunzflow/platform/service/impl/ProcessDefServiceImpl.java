package com.lunzflow.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunzflow.platform.dao.ProcessDefDao;
import com.lunzflow.platform.entity.ProcessDefEntity;
import com.lunzflow.platform.service.ProcessDefService;
import com.lunzflow.platform.util.Parametermap;
@Service
public class ProcessDefServiceImpl implements ProcessDefService {
	@Autowired
	private ProcessDefDao ProcessDefDao;
	@Override
	public List<ProcessDefEntity> queryPageAllProcessDef(Parametermap pm) {
		return ProcessDefDao.queryPageAllProcessDefPage(pm);
	}

}
