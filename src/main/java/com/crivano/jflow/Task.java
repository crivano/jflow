package com.crivano.jflow;

import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.TaskDefinition;

public interface Task<TD extends TaskDefinition<?, ?, ?, ?>, PI extends ProcessInstance<?, ?, ?>> {
	TaskResult execute(TD td, PI pi, Engine engine) throws Exception;
}
