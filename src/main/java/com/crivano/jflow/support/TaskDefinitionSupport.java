package com.crivano.jflow.support;

import java.util.List;

import com.crivano.jflow.model.ResponsibleKind;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.TaskDefinitionDetour;
import com.crivano.jflow.model.TaskDefinitionVariable;
import com.crivano.jflow.model.TaskKind;

public class TaskDefinitionSupport implements TaskDefinition {

	public TaskDefinitionSupport(String id, String name, TaskKind kind, String title, String after,
			ResponsibleKind responsibleKind, List<TaskDefinitionVariable> variable, List<TaskDefinitionDetour> detour,
			String subject, String text) {
		super();
		this.id = id;
		this.name = name;
		this.kind = kind;
		this.title = title;
		this.after = after;
		this.responsibleKind = responsibleKind;
		this.variable = variable;
		this.detour = detour;
		this.subject = subject;
		this.text = text;
	}

	public TaskDefinitionSupport() {
		super();
	}

	String id;

	String name;

	private TaskKind kind;

	private String title;

	private String after;

	private ResponsibleKind responsibleKind;

	private List<TaskDefinitionVariable> variable;

	private List<TaskDefinitionDetour> detour;

	private String subject;

	private String text;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public TaskKind getKind() {
		return kind;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getAfter() {
		return after;
	}

	@Override
	public ResponsibleKind getResponsibleKind() {
		return responsibleKind;
	}

	@Override
	public List<TaskDefinitionVariable> getVariable() {
		return variable;
	}

	@Override
	public List<TaskDefinitionDetour> getDetour() {
		return detour;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public String getText() {
		return text;
	}

}
