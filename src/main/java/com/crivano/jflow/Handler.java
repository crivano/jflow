package com.crivano.jflow;

import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.Responsible;

public interface Handler {
	void afterPause(ProcessInstance pi, TaskResult result);

	boolean evalCondition(ProcessInstance pi, String expression);

	TaskResult evalTask(ProcessInstance pi, String expression);

	String evalTemplate(ProcessInstance pi, String template);

	void sendEmail(Responsible responsible, String subject, String text);
}
