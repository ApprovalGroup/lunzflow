package com.lunzflow.platform.controller;

import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunzflow.platform.service.ActProcessTypeService;
import com.lunzflow.platform.service.ProcessDefService;
import com.lunzflow.platform.util.Parametermap;

@Controller
@RequestMapping("/group")
public class GroupController extends BaseController {
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	ActProcessTypeService actProcessTypeService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	HistoryService historyService;
	@Autowired
	IdentityService identityService;

	@Autowired
	ProcessDefService processDefService;

	@RequestMapping(value = "addGroupPage")
	public String addGroupPage() {

		return "page/group/addGroupPage";
	}

	@RequestMapping(value = "addGroup")
	public String addGroup(Model model) {
		Parametermap parametermap = getParametermap();
		String id = parametermap.get("id").toString();
		String name = parametermap.get("name").toString();
		
		GroupEntityImpl group = new GroupEntityImpl();
        group.setId(id);
        group.setName(name);
        group.setRevision(0);
		identityService.saveGroup(group);

		return "page/group/list";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Object contentpagelist(Model model) {

		Parametermap pm = this.getParametermap();

		pm.put("rows", identityService.createGroupQuery().list());

		return pm;
	}

}
