package com.lunzflow.platform.service;

import java.util.List;

import com.lunzflow.platform.entity.TaskAPIData;
import com.lunzflow.platform.entity.TaskData;

public interface CustomTaskService {
	

    /**
     * 分页查询代办事项
     * @param taskAPIData
     * @param pageSize
     * @return
     */
    List<TaskData> taskListPage(String userId);


    public List<TaskAPIData> queryByUserIdPage(String userId);

    
}
