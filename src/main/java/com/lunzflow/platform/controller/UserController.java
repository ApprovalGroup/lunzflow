package com.lunzflow.platform.controller;

import com.lunzflow.platform.service.ActProcessTypeService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunzflow.platform.service.ProcessDefService;
import com.lunzflow.platform.util.Parametermap;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
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

	@RequestMapping(value = "addUserPage")
	public String addUserPage() {

		return "page/user/addUserPage";
	}

	@RequestMapping(value = "addUser")
	public String addUser(Model model) {
		Parametermap parametermap = getParametermap();
		String id = parametermap.get("id").toString();
		String email = parametermap.get("email").toString();
		String firstName = parametermap.get("firstName").toString();
		String lastName = parametermap.get("lastName").toString();
		String password = parametermap.get("password").toString();
		String displayName = parametermap.get("displayName").toString();
		UserEntityImpl user = new UserEntityImpl();
		user.setId(id);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setDisplayName(displayName);
		user.setPassword(password);
		user.setRevision(0);
		identityService.saveUser(user);

		return "page/user/list";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Object contentpagelist(Model model) {

		Parametermap pm = this.getParametermap();

		pm.put("rows", identityService.createUserQuery().list());

		return pm;
	}

}
