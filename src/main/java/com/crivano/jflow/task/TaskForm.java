package com.crivano.jflow.task;

import java.util.List;
import java.util.Map;

import com.crivano.jflow.Engine;
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
import com.crivano.jflow.model.enm.VariableEditingKind;
import com.crivano.jflow.model.util.MissingParameterException;

public class TaskForm<PD extends ProcessDefinition<TD>, TD extends TaskDefinition<TK, RK, DV, DD>, R extends Responsible, TK extends TaskKind, RK extends ResponsibleKind, DV extends TaskDefinitionVariable, DD extends TaskDefinitionDetour, PI extends ProcessInstance<PD, TD, R>>
		implements PausableTask<TD, PI> {
	public String getEvent(TD td, PI pi) {
		return pi.getVariable().get("_codPrincipal") + "|" + td.getIdentifier();
	};

	@Override
	public TaskResult execute(TD td, PI pi, Engine engine) throws Exception {
		return new TaskResult(TaskResultKind.PAUSE, null, null, getEvent(td, pi), pi.calcResponsible(td));
	}

	@Override
	public TaskResult resume(TD td, PI pi, Integer detourIndex, Map<String, Object> param, Engine<?, ?, ?> engine)
			throws Exception {
		if (detourIndex == null && param == null)
			return execute(td, pi, engine);
		String detour = null;
		if (td.getDetour() != null && td.getDetour().size() > 0 && detourIndex != null
				&& detourIndex < td.getDetour().size())
			detour = ((TaskDefinitionDetour) td.getDetour().get(detourIndex)).getTaskIdentifier();

		if (td.getVariable() != null && td.getVariable().size() > 0) {
			for (DV v : (List<DV>) td.getVariable()) {
				if (v.getEditingKind() == VariableEditingKind.READ_ONLY)
					continue;
				Object value = param != null ? param.get(v.getIdentifier()) : null;
				if (v.getEditingKind() == VariableEditingKind.READ_WRITE_REQUIRED && value == null)
					throw new MissingParameterException(v.getIdentifier());
				pi.getVariable().put(v.getIdentifier(), value);
			}

		}

		return new TaskResult(TaskResultKind.DONE, detour, null, null, null);
	}

}
