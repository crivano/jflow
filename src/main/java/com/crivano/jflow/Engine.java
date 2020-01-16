package com.crivano.jflow;

import java.util.Map;

import com.crivano.jflow.model.ProcessInstance;

public interface Engine {

	void start(ProcessInstance pi) throws Exception;

	int resume(String event, Integer detourIndex, Map<String, Object> param) throws Exception;

	Handler getHandler();

}