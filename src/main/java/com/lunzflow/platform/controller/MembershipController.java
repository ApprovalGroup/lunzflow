package com.lunzflow.platform.controller;

import java.util.List;

import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunzflow.platform.service.ActProcessTypeService;
import com.lunzflow.platform.service.ProcessDefService;
import com.lunzflow.platform.util.Parametermap;

@Controller
@RequestMapping("/membership")
public class MembershipController extends BaseController {
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

	@RequestMapping(value = "addMembershipPage")
	public String addMembershipPage(Model model) {
		 List<User> users = identityService.createUserQuery().list();
		 List<Group> groups =  identityService.createGroupQuery().list();
		 model.addAttribute("users", users);
		 model.addAttribute("groups", groups);
		return "page/membership/addMembershipPage";
	}

	@RequestMapping(value = "addMembership")
	public String addMembership(Model model) {
		Parametermap parametermap = getParametermap();
		String userId = parametermap.get("userId").toString();
		String groupId = parametermap.get("groupId").toString();
		
		identityService.createMembership(userId, groupId);

		return "page/membership/list";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Object contentpagelist(Model model) {

		Parametermap pm = this.getParametermap();

		pm.put("rows", identityService.createGroupQuery().list());

		return pm;
	}

}
