package com.lunzflow.platform.dao;

import java.util.List;

import com.lunzflow.platform.entity.ProcessDefEntity;
import com.lunzflow.platform.util.Parametermap;

/**
 */
public interface ProcessDefDao {
    /**
     * 查询所有已经部署的流程
     * @param pm
     * @return
     */
    public List<ProcessDefEntity> queryPageAllProcessDefPage(Parametermap pm);

}
