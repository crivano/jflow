package com.crivano.jflow;

import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.TaskDefinition;

public interface Task {
	TaskResult execute(TaskDefinition td, ProcessInstance pi, Engine engine) throws Exception;
}
