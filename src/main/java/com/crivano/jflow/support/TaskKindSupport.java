package com.crivano.jflow.support;

import com.crivano.jflow.Task;
import com.crivano.jflow.model.TaskKind;
import com.crivano.jflow.task.TaskDecision;
import com.crivano.jflow.task.TaskEmail;
import com.crivano.jflow.task.TaskEval;
import com.crivano.jflow.task.TaskForm;

public enum TaskKindSupport implements TaskKind {

	FORM("User Filled Form", "rectangle", TaskForm.class),
	//
	DECISION("Decision", "diamond", TaskDecision.class),
	//
	EVAL("Eval", "rectangle", TaskEval.class),
	//
	EMAIL("Email", "folder", TaskEmail.class);

	private final String descr;

	private Class<? extends Task> clazz;

	private String graphKind;

	TaskKindSupport(String descr, String graphKind, Class<? extends Task> clazz) {
		this.descr = descr;
		this.graphKind = graphKind;
		this.clazz = clazz;
	}

	@Override
	public String getDescr() {
		return this.descr;
	}

	@Override
	public Class<? extends Task> getClazz() {
		return this.clazz;
	}

	@Override
	public String getGraphKind() {
		return this.graphKind;
	}

	@Override
	public String getGraphTitle() {
		return null;
	}
}
