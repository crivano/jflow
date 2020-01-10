package com.crivano.jflow;

import java.util.Map;

import com.crivano.jflow.model.ProcessDefinition;
import com.crivano.jflow.model.ProcessInstance;

public interface Engine {

	void start(ProcessInstance pi, ProcessDefinition definition, Map<String, Object> variable) throws Exception;

	int resume(String event, Integer detourIndex, Map<String, Object> param) throws Exception;

	Handler getHandler();

}