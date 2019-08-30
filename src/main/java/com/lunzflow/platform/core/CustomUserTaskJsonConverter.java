package com.lunzflow.platform.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.bpmn.model.BaseElement;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.editor.language.json.converter.BaseBpmnJsonConverter;
import org.flowable.editor.language.json.converter.UserTaskJsonConverter;

import com.fasterxml.jackson.databind.JsonNode;

public class CustomUserTaskJsonConverter extends UserTaskJsonConverter implements HmXMLConstants{

	public static void fillBpmnTypes(
			Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
		convertersToJsonMap.put(UserTask.class, CustomUserTaskJsonConverter.class);
	}

	@Override
	protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode,
			Map<String, JsonNode> shapeMap) {
		FlowElement flowElement = super.convertJsonToElement(elementNode, modelNode, shapeMap);
		if(flowElement instanceof UserTask) {
			UserTask userTask =  (UserTask) flowElement ;
			Map<String, List<ExtensionAttribute>> attributes = new HashMap<String, List<ExtensionAttribute>>();
			
			String approvalOperateType = getPropertyValueAsString(APPROVALOPERATE_TYPE, elementNode);
			String copyTo = getPropertyValueAsString(COPYTO, elementNode);
			String approval = getPropertyValueAsString(APPROVAL, elementNode);
			String operationSwitch = getPropertyValueAsString(OPERATIONSWITCH, elementNode);
			String timeoutReminder = getPropertyValueAsString(TIMEOUTREMINDER, elementNode);
			String timeout = getPropertyValueAsString(TIMEOUT, elementNode);
			
			ExtensionAttribute approvalOperateTypeAttr = ExtensionAttributeUtils.generate(APPROVALOPERATE_TYPE, approvalOperateType);
			ExtensionAttribute copyToAttr = ExtensionAttributeUtils.generate(COPYTO, copyTo);
			ExtensionAttribute approvalAttr = ExtensionAttributeUtils.generate(APPROVAL, approval);
			ExtensionAttribute operationSwitchAttr = ExtensionAttributeUtils.generate(OPERATIONSWITCH, operationSwitch);
			ExtensionAttribute timeoutReminderTypeAttr = ExtensionAttributeUtils.generate(TIMEOUTREMINDER, timeoutReminder);
			ExtensionAttribute timeoutAttr = ExtensionAttributeUtils.generate(TIMEOUT, timeout);
			
			attributes.put("custom", Arrays.asList(approvalOperateTypeAttr,copyToAttr,approvalAttr,operationSwitchAttr,timeoutReminderTypeAttr,timeoutAttr));
			userTask.setAttributes(attributes);
			
		}
		return flowElement;
	}
}
