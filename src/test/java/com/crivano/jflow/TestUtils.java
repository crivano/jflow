package com.crivano.jflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mvel2.MVEL;
import org.mvel2.templates.TemplateRuntime;

import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.Responsible;

public class TestUtils {
	public static class Context {
		ProcessInstance pi;

		Context(ProcessInstance pi) {
			this.pi = pi;
		}

		public void set(String var, Object value) {
			pi.getVariable().put(var, value);
		}
	}

	public static class ResponsibleWithEmail implements Responsible {
		String initials;
		String email;

		ResponsibleWithEmail(String initials, String email) {
			this.initials = initials;
			this.email = email;
		}

		@Override
		public String getInitials() {
			return initials;
		}

		public String getEmail() {
			return email;
		}
	}

	public static String sentEmail;

	// Creates the workflow engine, mocking the dao and the handler. Uses MVEL to
	// evaluate expressions.
	public static Engine buildEngine(ProcessInstance pi) {
		return new EngineImpl(new Dao() {

			@Override
			public void persist(ProcessInstance pi) {
			}

			@Override
			public List<ProcessInstance> listByEvent(String event) {
				List<ProcessInstance> l = new ArrayList<>();
				l.add(pi);
				return l;
			}

		}, new Handler() {

			@Override
			public void afterPause(ProcessInstance pi, TaskResult result) {
			}

			@Override
			public boolean evalCondition(ProcessInstance pi, String expression) {
				return MVEL.eval(expression, pi.getVariable(), Boolean.class);
			}

			@Override
			public TaskResult evalTask(ProcessInstance pi, String expression) {
				HashMap<String, Object> m = new HashMap<>();
				m.putAll(pi.getVariable());
				m.put("context", new Context(pi));
				return MVEL.eval(expression, m, TaskResult.class);
			}

			@Override
			public String evalTemplate(ProcessInstance pi, String template) {
				HashMap<String, Object> m = new HashMap<>();
				m.putAll(pi.getVariable());
				m.put("context", new Context(pi));
				m.put("td", pi.getCurrentTaskDefinition());
				m.put("to", pi.calcResponsible(pi.getCurrentTaskDefinition()));
				return (String) TemplateRuntime.eval(template, m);
			}

			@Override
			public void sendEmail(Responsible responsible, String subject, String text) {
				sentEmail = ((ResponsibleWithEmail) responsible).getEmail() + "|" + subject + "|" + text;
			}

		});
	}
}
