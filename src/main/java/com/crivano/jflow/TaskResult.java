package com.crivano.jflow;

import com.crivano.jflow.model.Responsible;
import com.crivano.jflow.model.enm.TaskResultKind;

public class TaskResult {
	public TaskResult(TaskResultKind kind, String detour, Throwable error, String event, Responsible responsible) {
		super();
		this.kind = kind;
		this.detour = detour;
		this.error = error;
		this.event = event;
		this.responsible = responsible;
	}

	TaskResultKind kind;
	String detour;
	Throwable error;
	String event;
	Responsible responsible;

	public TaskResultKind getKind() {
		return kind;
	}

	public String getDetour() {
		return detour;
	}

	public Throwable getError() {
		return error;
	}

	public String getEvent() {
		return event;
	}

	public Responsible getResponsible() {
		return responsible;
	}
}
