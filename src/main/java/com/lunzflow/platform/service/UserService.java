package com.lunzflow.platform.service;

import java.util.List;

import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;

/**
 * 用户dao
 */
public interface UserService {
    /**
     * 通过用户查询用户所属的组
     * @return
     */
    List<UserEntityImpl> queryUserByGroup(GroupEntityImpl group);
    /**
     * 通过组查询当前组中的人员
     * @return
     */
    List<GroupEntityImpl> queryGroupByUser(UserEntityImpl  user);
}
