package com.crivano.jflow;

import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.Responsible;

public interface Handler<PI extends ProcessInstance<?, ?, ?>, R extends Responsible> {
	void afterPause(PI pi, TaskResult result);

	boolean evalCondition(PI pi, String expression);

	TaskResult evalTask(PI pi, String expression);

	String evalTemplate(PI pi, String template);

	void sendEmail(R responsible, String subject, String text);
	
	void afterTransition(PI pi, Integer from, Integer to);
}
