package com.lunzflow.platform.core;

import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component("mulitiInstance")
public class MulitiInstanceCompleteCondition implements HmXMLConstants {

	public boolean completeTask(DelegateExecution execution) {
		FlowElement flowElement = execution.getCurrentFlowElement();
		System.out.println("总的会签任务数量：" + execution.getVariable("nrOfInstances") + "当前获取的会签任务数量："
				+ execution.getVariable("nrOfActiveInstances") + " - " + "已经完成的会签任务数量："
				+ execution.getVariable("nrOfCompletedInstances"));
		System.out.println("I am invoked.");

		// 审批操作类型：会签审批、或签审批、依次审批，默认会签
		String approvaloperatetype = flowElement.getAttributeValue(NAMESPACE, APPROVALOPERATE_TYPE);
		
		System.out.println("审批操作类型： " + approvaloperatetype);
		if ("或签审批".equals(approvaloperatetype)) {
			return true;
		}
//		if ("依次审批".equals(approvaloperatetype)) {
//			String activityId = execution.getCurrentActivityId();
//			System.out.println("当前结点id：" + flowElement.getId());
//			System.out.println("当前活动id：" + activityId);
//			System.out.println("当前流程id：" + execution.getProcessInstanceId());
//			// taskService.claim(taskId, userId);
//			// 驳回到指定节点
//			// TODO
//			// DealMultiInstanceTurnDown.backToStep(flowElement,execution.getProcessInstanceId())
//			return false;
//		}
		return false;
	}
}
