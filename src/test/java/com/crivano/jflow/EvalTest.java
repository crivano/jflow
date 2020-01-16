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

public class EvalTest {
	Engine engine;
	ProcessDefinitionSupport pd;
	TaskDefinitionSupport td;

	HashMap<String, Object> variable;
	ProcessInstanceSupport pi;

	@Before
	public void before() {

		// Create the process definition
		pd = new ProcessDefinitionSupport();

		String text = "";
		text += "import com.crivano.jflow.TaskResult; \n";
		text += "import com.crivano.jflow.model.enm.TaskResultKind; \n";
		text += "context.set(\"test\", \"OK\"); \n";
		text += "if (result == \"pause\") return new TaskResult(TaskResultKind.PAUSE, null, null, \"event\", null);";

		// Create the task definition
		td = new TaskDefinitionSupport("1", TaskKindSupport.EVAL, "Eval", null, null, null, null, null, text);
		pd.getTaskDefinition().add(td);

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
	public void singleEvalTask() throws Exception {
		variable.put("result", null);
		pi.setVariable(variable);

		engine.start(pi);

		assertEquals("OK", variable.get("test"));

		assertEquals(ProcessInstanceStatus.FINISHED, pi.getStatus());
	}

	@Test
	public void singleEvalTaskReturningResult() throws Exception {
		variable.put("result", "pause");
		pi.setVariable(variable);

		engine.start(pi);

		assertEquals("OK", variable.get("test"));

		assertEquals(ProcessInstanceStatus.PAUSED, pi.getStatus());
	}
}
