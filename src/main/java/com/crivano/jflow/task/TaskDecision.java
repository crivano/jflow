package com.crivano.jflow.task;

import java.util.Map;

import com.crivano.jflow.Engine;
import com.crivano.jflow.Handler;
import com.crivano.jflow.PausableTask;
import com.crivano.jflow.TaskResult;
import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.TaskDefinitionDetour;
import com.crivano.jflow.model.enm.TaskResultKind;

public class TaskDecision implements PausableTask {

	@Override
	public TaskResult execute(TaskDefinition td, ProcessInstance pi, Engine engine) {
		if (td.getDetour() == null || td.getDetour().size() == 0)
			return new TaskResult(TaskResultKind.DONE, null, null, null, null);
		for (TaskDefinitionDetour d : td.getDetour()) {
			if (d.getCondition() == null || verificarCondicao(d.getCondition(), pi, engine.getHandler()))
				return new TaskResult(TaskResultKind.DONE, d.getTaskIdentifier(), null, null, null);
		}

		// If there is no available detour, then pause
		return new TaskResult(TaskResultKind.PAUSE, null, null, getEvent(td, pi), null);
	}

	@Override
	public TaskResult resume(TaskDefinition td, ProcessInstance pi, Integer detourIndex, Map<String, Object> param,
			Engine engine) {
		return execute(td, pi, engine);
	}

	private boolean verificarCondicao(String expression, ProcessInstance pi, Handler handler) {
		return handler.evalCondition(pi, expression);
	}

	public static String getEvent(TaskDefinition td, ProcessInstance pi) {
		return "" + pi.getVariable().get("_codPrincipal");
	}
}
