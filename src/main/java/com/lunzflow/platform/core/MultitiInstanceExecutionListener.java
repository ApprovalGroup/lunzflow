package com.lunzflow.platform.core;

import java.util.Arrays;

import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;

public class MultitiInstanceExecutionListener implements ExecutionListener,HmXMLConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution execution) {
		
		FlowElement flowElement =execution.getCurrentFlowElement();
		
		String approval = flowElement.getAttributeValue(NAMESPACE, APPROVAL);
		
		System.out.println("当前审核节点: " + approval);
		
		if ("主管审2".equals(approval)) {
			execution.setVariable("assignees", Arrays.asList("manager1","manager2","manager3"));
		}else if ("总监审3-1".equals(approval)) {
			execution.setVariable("assignees", Arrays.asList("boss31","boss32","boss33"));
		}else if ("总监审3".equals(approval)) {
			execution.setVariable("assignees", Arrays.asList("boss"));
		}else {
			execution.setVariable("assignees", Arrays.asList("admin"));
		}
	}

}
