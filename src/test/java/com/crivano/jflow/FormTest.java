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
import com.crivano.jflow.support.ResponsibleKindSupport;
import com.crivano.jflow.support.TaskDefinitionSupport;
import com.crivano.jflow.support.TaskKindSupport;
import com.crivano.jflow.task.TaskForm;

public class FormTest {

	Engine engine;
	ProcessDefinition pd;
	TaskDefinition td;

	HashMap<String, Object> variable;
	ProcessInstance pi;

	@Before
	public void before() {

		// Create the process definition
		pd = new ProcessDefinitionSupport();

		// Create the task definition
		td = new TaskDefinitionSupport("1", "test", TaskKindSupport.FORM, "Form", null,
				ResponsibleKindSupport.REGISTRANT, null, null, null, null);
		pd.getTaskDefinition().add(td);

		// Create the process instance without responsible support
		variable = new HashMap<String, Object>();
		pi = new ProcessInstanceSupport(pd, variable, null) {
			@Override
			public Responsible calcResponsible(TaskDefinition tarefa) {
				return null;
			}
		};

		engine = TestUtils.buildEngine(pi);
	}

	@Test
	public void singleFormTask() throws Exception {

		// Start the process instance
		engine.start(pi, pd, variable);

		// The form is the first and only task definition, engine should wait for an
		// user event to continue
		assertEquals(ProcessInstanceStatus.PAUSED, pi.getStatus());

		// Graph should be composed of 3 nodes, the center one should be blue
		assertEquals(
				"\"start\"[shape=\"oval\"][color=\"black\"][fontcolor=\"black\"][label=<Start>];\"start\"->\"1\";\"finish\"[shape=\"oval\"][color=\"black\"][fontcolor=\"black\"][label=<Finish>];\"1\"[shape=\"rectangle\"][color=\"blue\"][fontcolor=\"blue\"][label=<Form>];\"1\"->\"finish\";",
				GraphViz.getDot(pi, "Start", "Finish"));

		// Resume after the user has filled the form
		engine.resume(TaskForm.getEvent(td, pi), null, null);

		// Workflow should be ended by now
		assertEquals(ProcessInstanceStatus.FINISHED, pi.getStatus());
	}

}
