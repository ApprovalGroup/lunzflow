package com.lunzflow.platform.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lunzflow.platform.service.ActProcessTypeService;
import com.lunzflow.platform.service.UserService;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lunzflow.platform.core.HmXMLConstants;
import com.lunzflow.platform.entity.ActProcessType;
import com.lunzflow.platform.service.ProcessDefService;
import com.lunzflow.platform.util.Parametermap;

@Controller
@RequestMapping("/process")

public class ProcessController extends BaseController implements HmXMLConstants{
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
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object contentpagelist(Model model) {

		Parametermap pm = this.getParametermap();

		pm.put("rows", processDefService.queryPageAllProcessDef(pm));

		return pm;
	}
	
	/**
	 * 根据实例查询，实例结束则查询绘制的流程图
	 * @param response
	 * @param request
	 * @param taskId
	 * @throws IOException
	 */
	@RequestMapping("/showActivityedimageDetailPage")
	public void showActivityedimageDetailPage(HttpServletResponse response, HttpServletRequest request,String processInstanceId) throws IOException {
		
		
		String processDefinitionId ="";
		List<String> highLightedActivities=new ArrayList<String>();
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		if (task==null) {
			HistoricProcessInstance hp = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			processDefinitionId=hp.getProcessDefinitionId();
		}else {
			 processDefinitionId = task.getProcessDefinitionId();
			 highLightedActivities.add(task.getTaskDefinitionKey());
		}
	
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new 	DefaultProcessDiagramGenerator();
		
		
		InputStream in = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "PNG", highLightedActivities,true);
		
		OutputStream out= response.getOutputStream();
        copyPic(in,out);
	}

	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public Object listView(Model model) {
		List<ActProcessType> selectAll = actProcessTypeService.selectAll();
		System.out.println(selectAll);
		model.addAttribute("actProcessTypes", selectAll);
		return "page/process/list";
	}
	
	/**
	 * @param model
	 * @param processId
	 * @param processCategory
	 * @return
	 */
	/**
	 * @param model
	 * @param processId
	 * @param processCategory
	 * @return
	 */
	/**
	 * @param model
	 * @param processId
	 * @param processCategory
	 * @return
	 */
	@RequestMapping("/startProcessInstance")
	public Object startProcessInstance(Model model,String processId,String processCategory) {
		Parametermap pm = this.getParametermap();
		
		String processDefineId = pm.get("processId").toString();
		//设置流程变量
		setVariables(processDefineId,pm);
		ProcessInstance startProcessInstanceById = runtimeService.startProcessInstanceById(pm.get("processId").toString(), pm);
		System.out.println("startProcessInstanceById:"+startProcessInstanceById);
		return new ModelAndView("redirect:listView");
	}

	//Request URL: http://127.0.0.1:9100/process/imageDetailPage?processId=leave:1:13c3f4b2-34c9-11e8-bdbd-4a18e9fe68f7
	@RequestMapping(value = "/showActivityimageDetailPage", method = RequestMethod.GET)
	public void imageDetailPage(String  taskId,HttpServletResponse response) throws IOException {
		//model.addAttribute("imageSrc", "/process/showImage?processId="+processId);
		//showActivityimageDetailPage
		
		Task task = taskService.createTaskQuery()
		.taskId(taskId).singleResult();
		String processDefinitionId = task.getProcessDefinitionId();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		DefaultProcessDiagramGenerator defaultProcessDiagramGenerator
		=new DefaultProcessDiagramGenerator();
		
		List<String> highLightedActivities=new ArrayList<>();
		highLightedActivities.add(task.getTaskDefinitionKey());
		InputStream in = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "PNG", highLightedActivities,true);
		
		OutputStream out = response.getOutputStream();
		copyPic(in,out);
	}
	@RequestMapping(value = "/imageDetailPage", method = RequestMethod.GET)
	public Object imageDetailPage(String  processId,Model model) {
		model.addAttribute("imageSrc", "/process/showImage?processId="+processId);
		return "page/process/imageDetailPage";
	}
	//http://127.0.0.1:9100/process/showImage?processId=leave:1:13c3f4b2-34c9-11e8-bdbd-4a18e9fe68f7
	
	@RequestMapping(value = "/showImage", method = RequestMethod.GET)
	public void showImage(String  processId,Model model,HttpServletRequest request,HttpServletResponse response) throws Throwable {
	
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processId);
		
		DefaultProcessDiagramGenerator defaultProcessDiagramGenerator
		=new DefaultProcessDiagramGenerator();
		
		List<String> highLightedActivities=new ArrayList<>();
		InputStream in = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "PNG", highLightedActivities,true);
		
		OutputStream out = response.getOutputStream();
		copyPic(in,out);
	}
	
	@RequestMapping("/startFormPage")
	public String startFormPage(Model model,String processId,String processCategory) {
		model.addAttribute("processId", processId);
		return "page/process/startForm";
	}
	
	private void copyPic(InputStream in, OutputStream out) {
		try {
			IOUtils.copy(in, out);
		} catch (IOException e) {
		}finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
		}
	}

	@RequestMapping(value = "uploadToView")
	public String uploadToView(Model model) {
		List<ActProcessType> list = actProcessTypeService.selectAll();
		model.addAttribute("actProcessTypes", list);
		
		return "page/process/editcontentpage";
	}


	@RequestMapping(value = "/upload")
	public ModelAndView upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
//actProcessTypes
		Parametermap pm = getParametermap();
		 upload(request, file, pm);
		return new ModelAndView("redirect:listView");
	}

	private void upload(HttpServletRequest request, MultipartFile file, Parametermap pm) throws Exception {
		System.out.println("开始上传文件了");

		request.setCharacterEncoding("UTF-8");

		if (!file.isEmpty()) {
			String filename = file.getOriginalFilename();
			System.out.println(filename);
			String type = filename.indexOf(".") != -1
					? filename.substring(filename.lastIndexOf(".") + 1, filename.length())
					: null;
			if (type != null) {
				if (type.endsWith("zip")) {
					ZipInputStream zs=new ZipInputStream(file.getInputStream());
					repositoryService
					.createDeployment()
					.name(pm.get("name").toString())
					.category(pm.get("category").toString())
					.addZipInputStream(zs)
					.deploy();
					
				}else if(type.endsWith("bpmn")|| type.endsWith("xml")) {
					InputStream inputStream=file.getInputStream();
					
					repositoryService.createDeployment()
					.name(pm.get("name").toString())
					.category(pm.get("category").toString())
					.addInputStream(filename, inputStream)
					.deploy();
				}
			}
		}
	}
	
	/**
	 * 获取流程定义的中的自定义属性，并设置流程变量
	 * @param processDefineId
	 * @param pm
	 */
	private void setVariables(String processDefineId, Parametermap pm) {
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
