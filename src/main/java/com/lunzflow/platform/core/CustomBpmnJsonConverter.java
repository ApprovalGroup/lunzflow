package com.lunzflow.platform.core;

import org.flowable.editor.language.json.converter.BpmnJsonConverter;

public class CustomBpmnJsonConverter extends BpmnJsonConverter {
	static {
		convertersToBpmnMap.put(STENCIL_TASK_USER, CustomUserTaskJsonConverter.class);
	}

}
