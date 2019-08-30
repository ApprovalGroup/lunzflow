package com.lunzflow.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunzflow.platform.dao.TaskDao;
import com.lunzflow.platform.entity.TaskAPIData;
import com.lunzflow.platform.entity.TaskData;
import com.lunzflow.platform.service.CustomTaskService;
@Service
public class CustomTaskServiceImpl  implements CustomTaskService{
	@Autowired
	private TaskDao taskDao;
	@Override
	public List<TaskData> taskListPage(String userId) {
		return taskDao.queryByUserIdListPage(userId);
	}
	
	@Override
	public List<TaskAPIData> queryByUserIdPage(String userId) {
		return taskDao.queryByUserIdPage(userId);
	}

}
