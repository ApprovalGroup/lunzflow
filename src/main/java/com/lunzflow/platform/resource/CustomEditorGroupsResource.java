package com.lunzflow.platform.resource;

import java.util.ArrayList;
import java.util.List;

import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.ui.common.model.GroupRepresentation;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class CustomEditorGroupsResource {

	@Autowired
	private IdentityService identityService;

	@RequestMapping(value = "/rest/editor-groups", method = RequestMethod.GET)
	public ResultListDataRepresentation getGroups(@RequestParam(required = false, value = "filter") String filter) {

		List<GroupRepresentation> result = new ArrayList<>();
		List<Group> groups = new ArrayList<>();
		if (filter == null || "".equals(filter)) {
			groups = identityService.createGroupQuery().list();
		} else {
			groups = identityService.createGroupQuery().groupNameLike("%" + filter + "%").list();
		}

		for (Group group : groups) {
			result.add(new GroupRepresentation(group));
		}
		return new ResultListDataRepresentation(result);
	}
}
