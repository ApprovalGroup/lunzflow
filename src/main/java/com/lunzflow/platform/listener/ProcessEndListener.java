package com.lunzflow.platform.listener;

import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.impl.event.FlowableEngineEventImpl;
import org.thymeleaf.spring5.context.SpringContextUtils;

public class ProcessEndListener implements FlowableEventListener {

	@Override
	public void onEvent(FlowableEvent event) {
		 FlowableEngineEventImpl  engineEvent=(FlowableEngineEventImpl)event;
	}

	@Override
	public boolean isFailOnException() {
		return false;
	}

	@Override
	public boolean isFireOnTransactionLifecycleEvent() {
		return false;
	}

	@Override
	public String getOnTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

}
