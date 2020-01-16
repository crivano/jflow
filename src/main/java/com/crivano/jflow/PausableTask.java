package com.crivano.jflow;

import java.util.Map;

import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.TaskDefinition;

public interface PausableTask<TD extends TaskDefinition<?, ?, ?, ?>, PI extends ProcessInstance<?, ?, ?>>
		extends Task<TD, PI> {
	TaskResult resume(TD td, PI pi, Integer detourIndex, Map<String, Object> param, Engine engine);
}
