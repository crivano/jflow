package com.crivano.jflow.support;

import java.util.ArrayList;
import java.util.List;

import com.crivano.jflow.model.ProcessDefinition;
import com.crivano.jflow.model.TaskDefinition;

public class ProcessDefinitionSupport implements ProcessDefinition<TaskDefinitionSupport> {
	private List<TaskDefinitionSupport> tarefa = new ArrayList<>();

	@Override
	public List<TaskDefinitionSupport> getTaskDefinition() {
		return tarefa;
	}
}
