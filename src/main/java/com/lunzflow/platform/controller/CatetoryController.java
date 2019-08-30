package com.lunzflow.platform.controller;

import com.lunzflow.platform.service.ActProcessTypeService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunzflow.platform.entity.ActProcessType;
import com.lunzflow.platform.service.ProcessDefService;
import com.lunzflow.platform.util.Parametermap;

@Controller
@RequestMapping("/category")
public class CatetoryController extends BaseController {
	@Autowired
	RepositoryService repositoryService;
	@Autowired
    ActProcessTypeService actProcessTypeService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	HistoryService historyService;
	@Autowired
	TaskService taskService;
	
	@Autowired
	ProcessDefService processDefService;
	
	
	@RequestMapping(value = "createCategory")
	public String createCategory() {
		
		return "page/categroy/createCategory";
	}

	@RequestMapping(value = "createTemplate")
	public String createTemplate() {
		
		return "page/categroy/createTemplate";
	}
	@RequestMapping(value = "addCategory")
	public String addCategory(Model model) {
		Parametermap parametermap = getParametermap();
		String name = parametermap.get("name").toString();
		String code =  parametermap.get("code").toString();
		
		ActProcessType record = new ActProcessType();
		record.setCodeId(code);
		record.setName(name);
		record.setPid(0);
		record.setState(0L);
		actProcessTypeService.insert(record);
		
		return "page/categroy/list";
	}
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object contentpagelist(Model model) {

		Parametermap pm = this.getParametermap();

		pm.put("rows", actProcessTypeService.selectAll());

		return pm;
	}

}
