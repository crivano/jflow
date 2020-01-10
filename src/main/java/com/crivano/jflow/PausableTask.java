package com.crivano.jflow;

import java.util.Map;

import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.TaskDefinition;

public interface PausableTask extends Task {
	TaskResult resume(TaskDefinition td, ProcessInstance pi, Integer detourIndex, Map<String, Object> param,
			Engine engine);
}
