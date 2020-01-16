package com.crivano.jflow.model.util;

import java.util.Map;

import com.crivano.jflow.Engine;
import com.crivano.jflow.Handler;
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
	public int resume(String evento, Integer indiceDesvio, Map<String, Object> parametro) throws Exception {
		if (delegate == null)
			return 0;
		return delegate.resume(evento, indiceDesvio, parametro);
	}

	@Override
	public Handler getHandler() {
		return delegate.getHandler();
	}

}
