package com.crivano.jflow.task;

import com.crivano.jflow.Engine;
import com.crivano.jflow.Task;
import com.crivano.jflow.TaskResult;
import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.Responsible;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.enm.TaskResultKind;

public class TaskEmail implements Task<TaskDefinition, ProcessInstance> {

	@Override
	public TaskResult execute(TaskDefinition td, ProcessInstance pi, Engine engine) {
		String subject = engine.getHandler().evalTemplate(pi, td.getSubject());
		String text = engine.getHandler().evalTemplate(pi, td.getText());
		Responsible responsible = pi.calcResponsible(td);
		engine.getHandler().sendEmail(responsible, subject, text);
		return new TaskResult(TaskResultKind.DONE, null, null, null, null);
	}

}
