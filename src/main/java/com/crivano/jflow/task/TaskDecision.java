package com.crivano.jflow.task;

import java.util.List;
import java.util.Map;

import com.crivano.jflow.Engine;
import com.crivano.jflow.Handler;
import com.crivano.jflow.PausableTask;
import com.crivano.jflow.TaskResult;
import com.crivano.jflow.model.ProcessDefinition;
import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.Responsible;
import com.crivano.jflow.model.ResponsibleKind;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.TaskDefinitionDetour;
import com.crivano.jflow.model.TaskDefinitionVariable;
import com.crivano.jflow.model.TaskKind;
import com.crivano.jflow.model.enm.TaskResultKind;

public class TaskDecision<PD extends ProcessDefinition<TD>, TD extends TaskDefinition<TK, RK, DV, DD>, R extends Responsible, TK extends TaskKind, RK extends ResponsibleKind, DV extends TaskDefinitionVariable, DD extends TaskDefinitionDetour, PI extends ProcessInstance<PD, TD, R>>
		implements PausableTask<TD, PI> {

	@Override
	public TaskResult execute(TD td, PI pi, Engine engine) throws Exception {
		if (td.getDetour() == null || td.getDetour().size() == 0)
			return new TaskResult(TaskResultKind.DONE, null, null, null, null);
		for (TaskDefinitionDetour d : (List<TaskDefinitionDetour>) td.getDetour()) {
			if (d.getCondition() == null || verificarCondicao(d.getCondition(), pi, engine.getHandler()))
				return new TaskResult(TaskResultKind.DONE, d.getTaskIdentifier(), null, null, null);
		}

		// If there is no available detour, then pause
		return new TaskResult(TaskResultKind.PAUSE, null, null, getEvent(td, pi), null);
	}

	@Override
	public TaskResult resume(TD td, PI pi, Integer detourIndex, Map<String, Object> param, Engine<?, ?, ?> engine)
			throws Exception {
		return execute(td, pi, engine);
	}

	public static String getEvent(TaskDefinition td, ProcessInstance pi) {
		return "" + pi.getVariable().get("_codPrincipal");
	}

	private boolean verificarCondicao(String expression, ProcessInstance pi, Handler handler) {
		return handler.evalCondition(pi, expression);
	}

}
