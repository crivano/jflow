package com.crivano.jflow.task;

import java.util.Map;

import com.crivano.jflow.Engine;
import com.crivano.jflow.PausableTask;
import com.crivano.jflow.TaskResult;
import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.TaskDefinitionVariable;
import com.crivano.jflow.model.enm.TaskResultKind;
import com.crivano.jflow.model.enm.VariableEditingKind;

public class TaskForm implements PausableTask {

	@Override
	public TaskResult execute(TaskDefinition tarefa, ProcessInstance procedimento, Engine engine) {
		return new TaskResult(TaskResultKind.PAUSE, null, null, getEvent(tarefa, procedimento),
				procedimento.calcResponsible(tarefa));
	}

	@Override
	public TaskResult resume(TaskDefinition td, ProcessInstance pi, Integer detourIndex, Map<String, Object> param,
			Engine engine) {
		String detour = null;
		if (td.getDetour() != null && td.getDetour().size() > 0 && detourIndex != null
				&& detourIndex < td.getDetour().size())
			detour = td.getDetour().get(detourIndex).getTaskIdentifier();

		if (td.getVariable() != null && td.getVariable().size() > 0) {
			for (TaskDefinitionVariable v : td.getVariable()) {
				if (v.getEditingKind() == VariableEditingKind.READ_WRITE)
					pi.getVariable().put(v.getIdentifier(), param.get(v.getIdentifier()));
			}

		}

		return new TaskResult(TaskResultKind.DONE, detour, null, null, null);
	}

	public static String getEvent(TaskDefinition tarefa, ProcessInstance procedimento) {
		return procedimento.getVariable().get("_codPrincipal") + "|" + tarefa.getIdentifier();
	}

}
