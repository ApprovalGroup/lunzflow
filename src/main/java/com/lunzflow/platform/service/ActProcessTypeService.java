package com.lunzflow.platform.service;

import java.util.List;

import com.lunzflow.platform.entity.ActProcessType;

public interface ActProcessTypeService {
	   List<ActProcessType> selectAll();
	   public int insert(ActProcessType record);
}
