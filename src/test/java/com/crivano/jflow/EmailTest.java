package com.crivano.jflow;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.crivano.jflow.model.ProcessDefinition;
import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.Responsible;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.enm.ProcessInstanceStatus;
import com.crivano.jflow.support.ProcessDefinitionSupport;
import com.crivano.jflow.support.ProcessInstanceSupport;
import com.crivano.jflow.support.TaskDefinitionSupport;
import com.crivano.jflow.support.TaskKindSupport;

public class EmailTest {
	Engine engine;
	ProcessDefinition pd;
	TaskDefinition td;

	HashMap<String, Object> variable;
	ProcessInstance pi;

	@Before
	public void before() {

		// Create the process definition
		pd = new ProcessDefinitionSupport();

		String subject = "Hi!";
		String text = "Wellcome ${to.initials}!";

		// Create the task definition
		td = new TaskDefinitionSupport("1", TaskKindSupport.EMAIL, "Email", null, null, null, null, subject,
				text);
		pd.getTaskDefinition().add(td);

		// Create the process instance without responsible support
		variable = new HashMap<String, Object>();
		pi = new ProcessInstanceSupport(pd, variable, null) {
			@Override
			public Responsible calcResponsible(TaskDefinition tarefa) {
				return new TestUtils.ResponsibleWithEmail("NAME", "test@example.com");
			}
		};

		engine = TestUtils.buildEngine(pi);
	}

	@Test
	public void singleEmailTask() throws Exception {
		engine.start(pi);

		assertEquals("test@example.com|Hi!|Wellcome NAME!", TestUtils.sentEmail);

		assertEquals(ProcessInstanceStatus.FINISHED, pi.getStatus());
	}

}
