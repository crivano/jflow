package com.crivano.jflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mvel2.MVEL;
import org.mvel2.templates.TemplateRuntime;

import com.crivano.jflow.support.ProcessInstanceSupport;
import com.crivano.jflow.support.ResponsibleSupport;

public class TestUtils {
	public static class Context {
		ProcessInstanceSupport pi;

		Context(ProcessInstanceSupport pi) {
			this.pi = pi;
		}

		public void set(String var, Object value) {
			pi.getVariable().put(var, value);
		}
	}

	public static String sentEmail;

	// Creates the workflow engine, mocking the dao and the handler. Uses MVEL to
	// evaluate expressions.
	public static Engine buildEngine(final ProcessInstanceSupport pi) {
		return new EngineImpl(new Dao<ProcessInstanceSupport>() {

			@Override
			public void persist(ProcessInstanceSupport pi) {
			}

			@Override
			public List<ProcessInstanceSupport> listByEvent(String event) {
				List<ProcessInstanceSupport> l = new ArrayList<>();
				l.add(pi);
				return l;
			}

		}, new Handler<ProcessInstanceSupport, ResponsibleSupport>() {

			@Override
			public void afterPause(ProcessInstanceSupport pi, TaskResult result) {
			}

			@Override
			public boolean evalCondition(ProcessInstanceSupport pi, String expression) {
				return MVEL.eval(expression, pi.getVariable(), Boolean.class);
			}

			@Override
			public TaskResult evalTask(ProcessInstanceSupport pi, String expression) {
				HashMap<String, Object> m = new HashMap<>();
				m.putAll(pi.getVariable());
				m.put("context", new Context(pi));
				return MVEL.eval(expression, m, TaskResult.class);
			}

			@Override
			public String evalTemplate(ProcessInstanceSupport pi, String template) {
				HashMap<String, Object> m = new HashMap<>();
				m.putAll(pi.getVariable());
				m.put("context", new Context(pi));
				m.put("td", pi.getCurrentTaskDefinition());
				m.put("to", pi.calcResponsible(pi.getCurrentTaskDefinition()));
				return (String) TemplateRuntime.eval(template, m);
			}

			@Override
			public void sendEmail(ResponsibleSupport responsible, String subject, String text) {
				sentEmail = ((ResponsibleSupport) responsible).getEmail() + "|" + subject + "|" + text;
			}

		});
	}
}
