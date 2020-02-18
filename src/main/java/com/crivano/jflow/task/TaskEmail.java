package com.crivano.jflow.task;

import com.crivano.jflow.Engine;
import com.crivano.jflow.Task;
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

public class TaskEmail<PD extends ProcessDefinition<TD>, TD extends TaskDefinition<TK, RK, DV, DD>, R extends Responsible, TK extends TaskKind, RK extends ResponsibleKind, DV extends TaskDefinitionVariable, DD extends TaskDefinitionDetour, PI extends ProcessInstance<PD, TD, R>>
		implements Task<TD, PI> {

	@Override
	public TaskResult execute(TD td, PI pi, Engine engine) throws Exception {
		String subject = engine.getHandler().evalTemplate(pi, td.getSubject());
		String text = engine.getHandler().evalTemplate(pi, td.getText());
		Responsible responsible = pi.calcResponsible(td);
		engine.getHandler().sendEmail(responsible, subject, text);
		return new TaskResult(TaskResultKind.DONE, null, null, null, null);
	}

}
