package com.lunzflow.platform.resource;

import java.util.ArrayList;
import java.util.List;

import org.flowable.engine.IdentityService;
import org.flowable.idm.api.User;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.flowable.ui.common.model.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class CustomEditorUsersResource {

	@Autowired
	private IdentityService identityService;

	@RequestMapping(value = "/rest/editor-users", method = RequestMethod.GET)
	public ResultListDataRepresentation getUsers(@RequestParam(value = "filter", required = false) String filter) {
		List<User> users = new ArrayList<>();
		
		if (filter == null || "".equals(filter)) {
			users = identityService.createUserQuery().list();
		} else {
			users = identityService.createUserQuery().userFullNameLikeIgnoreCase("%" + filter + "%").list();
		}

		List<UserRepresentation> userRepresentations = new ArrayList<>(users.size());
		for (User user : users) {
			userRepresentations.add(new UserRepresentation(user));
		}
		return new ResultListDataRepresentation(userRepresentations);
	}
}
