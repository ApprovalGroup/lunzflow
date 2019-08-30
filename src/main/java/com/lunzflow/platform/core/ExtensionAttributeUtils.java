package com.lunzflow.platform.core;

import org.flowable.bpmn.model.ExtensionAttribute;

public class ExtensionAttributeUtils implements HmXMLConstants{
	public static ExtensionAttribute generate(String name,String value) {
		ExtensionAttribute ea = new ExtensionAttribute();
		ea.setNamespace(NAMESPACE);
		ea.setNamespacePrefix(NAMESPACE_PREFIX);
		ea.setName(name);
		ea.setValue(value);
		return ea;
	}

}
