package com.lunzflow.platform.service.impl;

import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.ui.modeler.service.ModelServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.lunzflow.platform.core.CustomBpmnJsonConverter;

@Service("customModelServiceImpl")
@Primary
public class CustomModelServiceImpl extends ModelServiceImpl{

	protected BpmnJsonConverter bpmnJsonConverter= new CustomBpmnJsonConverter();
}
