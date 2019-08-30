package com.lunzflow.platform.listener;


import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class CommonUserStartListener implements TaskListener {

    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(CommonUserStartListener.class);


    @Override
    public void notify(DelegateTask delegateTask) {
        log.warn("进入通用用户任务启动监听器");

        //查询信息
        log.info("任务执行人："+delegateTask.getAssignee());
        log.info("任务配置ID: " +delegateTask.getTaskDefinitionKey());

        //查询变量
        Set<String> setNames= delegateTask.getVariableNames();
        if (!CollectionUtils.isEmpty(setNames)){
            log.info("任务变量:"+setNames.toString());
        }

        for (String varName :setNames){
            Object varValue= delegateTask.getVariable(varName);
            log.info("变量名:"+varName+" 变量值:"+ JSON.toJSONString(varValue));
        }

        //修改变量
        delegateTask.setVariable("Test_Var","测试变量");

        log.warn("退出通用用户任务启动监听器");
    }
}


