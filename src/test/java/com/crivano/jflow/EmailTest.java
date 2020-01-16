package com.crivano.jflow;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.crivano.jflow.model.enm.ProcessInstanceStatus;
import com.crivano.jflow.support.ProcessDefinitionSupport;
import com.crivano.jflow.support.ProcessInstanceSupport;
import com.crivano.jflow.support.ResponsibleSupport;
import com.crivano.jflow.support.TaskDefinitionSupport;
import com.crivano.jflow.support.TaskKindSupport;

public class EmailTest {
	Engine engine;
	ProcessDefinitionSupport pd;
	TaskDefinitionSupport td;

	HashMap<String, Object> variable;
	ProcessInstanceSupport pi;

	@Before
	public void before() {

		// Create the process definition
		pd = new ProcessDefinitionSupport();

		String subject = "Hi!";
		String text = "Wellcome ${to.initials}!";

		// Create the task definition
		td = new TaskDefinitionSupport("1", TaskKindSupport.EMAIL, "Email", null, null, null, null, subject, text);
		pd.getTaskDefinition().add(td);

		// Create the process instance without responsible support
		variable = new HashMap<String, Object>();
		pi = new ProcessInstanceSupport(pd, variable) {
			@Override
			public ResponsibleSupport calcResponsible(TaskDefinitionSupport tarefa) {
				return new ResponsibleSupport("NAME", "test@example.com");
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
