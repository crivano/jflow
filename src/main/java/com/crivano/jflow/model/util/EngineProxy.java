package com.crivano.jflow.model.util;

import java.util.Map;

import com.crivano.jflow.Engine;
import com.crivano.jflow.Handler;
import com.crivano.jflow.TaskResult;
import com.crivano.jflow.model.ProcessInstance;

public class EngineProxy implements Engine {
	private static EngineProxy uniqueInstance = new EngineProxy();
	private Engine delegate = null;

	private EngineProxy() {
	}

	public static EngineProxy getInstance() {
		return uniqueInstance;
	}

	public void setDelegate(Engine d) {
		delegate = d;
	}

	@Override
	public void start(ProcessInstance pi) throws Exception {
		if (delegate == null)
			return;
		delegate.start(pi);
	}

	@Override
	public Handler getHandler() {
		return delegate.getHandler();
	}

	@Override
	public int resume(String event, Integer detourIndex, Map param) throws Exception {
		if (delegate == null)
			return 0;
		return delegate.resume(event, detourIndex, param);
	}

	@Override
	public TaskResult execute(ProcessInstance pi, Integer from, int to) throws Exception {
		if (delegate == null)
			return null;
		return delegate.execute(pi, from, to);
	}

}
