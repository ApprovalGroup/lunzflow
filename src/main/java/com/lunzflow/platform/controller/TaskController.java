package com.lunzflow.platform.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lunzflow.platform.service.ActProcessTypeService;
import com.lunzflow.platform.service.UserService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lunzflow.platform.Const.Const;
import com.lunzflow.platform.core.HmXMLConstants;
import com.lunzflow.platform.service.CustomTaskService;
import com.lunzflow.platform.service.ProcessDefService;
import com.lunzflow.platform.util.Parametermap;

@Controller
@RequestMapping("/task")
public class TaskController extends BaseController implements HmXMLConstants {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ActProcessTypeService processTypeService;
	@Autowired
	private ProcessDefService processDefService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private CustomTaskService customTaskService;
	@Autowired
	UserService userService;
	@Autowired
	private org.flowable.engine.TaskService rowtaskService;
	Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	
	@RequestMapping(value = "taskListPage")
	public Object taskListPage(HttpServletRequest request) {
		return "page/task/list";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object contentpagelist(Model model) {

		Parametermap pm = getParametermap();
		UserEntityImpl user = (UserEntityImpl) getSession().getAttribute(Const.SESSION_USER);
		pm.put("rows",customTaskService.taskListPage(user.getId()));
		return pm;
	}
	
	@RequestMapping("/taskHandler")
	public String startFormPage(Model model, String taskId) {
		Task task = rowtaskService.createTaskQuery().taskId(taskId).singleResult();
		String executionId = task.getExecutionId();
		Map<String, Object> variables = runtimeService.getVariables(executionId);
		System.out.println(variables);
		model.addAttribute("taskId", taskId);
		model.addAllAttributes(variables);
		return "page/task/taskHandler";
	}
	
	@RequestMapping("/complete")
	public ModelAndView completeTask(Model model, String taskId) {
		Parametermap pm = this.getParametermap();
		UserEntityImpl user = (UserEntityImpl) getSession().getAttribute(Const.SESSION_USER);
		pm.remove("taskId");
		//设置流程变量
		setVariables(pm);
		rowtaskService.complete(taskId, pm);
		return new ModelAndView("redirect:taskListPage");
	}
	
	@RequestMapping(value = "taskHandleredListPage")
	public Object taskHandleredListPage(HttpServletRequest request) {

		/// flowable-web/src/main/resources/templates/page/task
		return "page/task/taskHandleredList";
	}

	@RequestMapping(value = "/taskHandleredList")
	@ResponseBody
	public Object taskHandleredList(Model model) {

		Parametermap pm = getParametermap();
		UserEntityImpl user = (UserEntityImpl) getSession().getAttribute(Const.SESSION_USER);
		
		List<GroupEntityImpl> groups = userService.queryGroupByUser(user);
		if(groups.size()> 0 && groups != null) {
			if(groups.get(0)!=null) {
				pm.put(CURRENT_USER_DEPT, groups.get(0).getName());
			}
			
		}
		pm.put("rows", customTaskService.queryByUserIdPage(user.getId()));

		return pm;
	}
	/**
	 * 设置流程变量
	 * @param pm
	 */
	private void setVariables(Parametermap pm) {
		String sponsor = pm.get("sponsor").toString();
		String reportWriter = pm.get("reportWriter").toString();
		//发起人所属的部门
		String sponsorDept = "" ;
		//撰写人所属组
		String reportWriterDept = "" ;
		UserEntityImpl user = new UserEntityImpl();
		user.setId(sponsor);
		List<GroupEntityImpl> groups = userService.queryGroupByUser(user);
		if(groups.size()> 0 && groups != null) {
			if(groups.get(0)!=null) {
				
				sponsorDept = groups.get(0).getName();
			}
			
		}
		
		user.setId(reportWriter);
		
		groups = userService.queryGroupByUser(user);
		
		if(groups.size()> 0 && groups != null) {
			if(groups.get(0)!=null) {
				
				reportWriterDept = groups.get(0).getName();
			}
		}
		pm.put(REPORTWRITERDEPT, reportWriterDept);
		pm.put(SPONSOR, sponsorDept);
		
	}
}
