package com.crivano.jflow;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.crivano.jflow.model.enm.ProcessInstanceStatus;
import com.crivano.jflow.support.DetourSupport;
import com.crivano.jflow.support.ProcessDefinitionSupport;
import com.crivano.jflow.support.ProcessInstanceSupport;
import com.crivano.jflow.support.ResponsibleKindSupport;
import com.crivano.jflow.support.ResponsibleSupport;
import com.crivano.jflow.support.TaskDefinitionSupport;
import com.crivano.jflow.support.TaskKindSupport;
import com.crivano.jflow.task.TaskDecision;

public class DecisionTest {

	Engine engine;
	ProcessDefinitionSupport pd;
	TaskDefinitionSupport td, td2, td3;

	HashMap<String, Object> variable;
	ProcessInstanceSupport pi;

	@Before
	public void before() {

		// Create the process definition
		pd = new ProcessDefinitionSupport();

		// Create the task definition
		td = new TaskDefinitionSupport("1", TaskKindSupport.DECISION, "Decision", null,
				ResponsibleKindSupport.REGISTRANT, null, new ArrayList<>(), null, null);
		td.getDetour().add(new DetourSupport("D1", "2", "d =='2'"));
		td.getDetour().add(new DetourSupport("D2", "3", "d =='3'"));
		pd.getTaskDefinition().add(td);

		// Create 2 form task definitions
		td2 = new TaskDefinitionSupport("2", TaskKindSupport.FORM, "Form", null, ResponsibleKindSupport.REGISTRANT,
				null, null, null, null);
		pd.getTaskDefinition().add(td2);

		td3 = new TaskDefinitionSupport("3", TaskKindSupport.FORM, "Form", null, ResponsibleKindSupport.REGISTRANT,
				null, null, null, null);
		pd.getTaskDefinition().add(td3);

		// Create the process instance without responsible support
		variable = new HashMap<String, Object>();
		pi = new ProcessInstanceSupport(pd, variable) {
			@Override
			public ResponsibleSupport calcResponsible(TaskDefinitionSupport tarefa) {
				return null;
			}
		};

		engine = TestUtils.buildEngine(pi);
	}

	@Test
	public void singleDecisionTask() throws Exception {

		// Decision should detour to the form with id == "2"
		variable.put("d", "2");
		pi.setVariable(variable);
		engine.start(pi);
		assertEquals(pi.getStatus(), ProcessInstanceStatus.PAUSED);
		assertEquals("2", pi.getCurrentTaskDefinition().getIdentifier());

		// Decision should detour to the form with id == "3"
		variable.put("d", "3");
		pi.setVariable(variable);
		engine.start(pi);
		assertEquals(pi.getStatus(), ProcessInstanceStatus.PAUSED);
		assertEquals("3", pi.getCurrentTaskDefinition().getIdentifier());

		// There is no detour that would evaluate to true, therefore the engine should
		// pause at the decision and wait for an event to continue
		variable.put("d", null);
		pi.setVariable(variable);
		engine.start(pi);
		assertEquals(pi.getStatus(), ProcessInstanceStatus.PAUSED);
		assertEquals("1", pi.getCurrentTaskDefinition().getIdentifier());

		variable.put("d", "2");
		engine.resume(TaskDecision.getEvent(td, pi), null, null);
		assertEquals(pi.getStatus(), ProcessInstanceStatus.PAUSED);
		assertEquals("2", pi.getCurrentTaskDefinition().getIdentifier());
	}

}
