package com.crivano.jflow.task;

import java.util.Map;

import com.crivano.jflow.Engine;
import com.crivano.jflow.PausableTask;
import com.crivano.jflow.TaskResult;
import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.enm.TaskResultKind;

public class TaskEval implements PausableTask {

	@Override
	public TaskResult execute(TaskDefinition td, ProcessInstance pi, Engine engine) {
		TaskResult result = engine.getHandler().evalTask(pi, td.getText());
		if (result != null)
			return result;
		return new TaskResult(TaskResultKind.DONE, null, null, null, null);
	}

	@Override
	public TaskResult resume(TaskDefinition td, ProcessInstance pi, Integer detourIndex, Map<String, Object> param,
			Engine engine) {
		return execute(td, pi, engine);
	}

	public static String getEvent(TaskDefinition td, ProcessInstance pi) {
		return pi.getVariable().get("_codPrincipal") + "|" + td.getId();
	}

}
