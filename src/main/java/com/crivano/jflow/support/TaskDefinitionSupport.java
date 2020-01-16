package com.crivano.jflow.support;

import java.util.List;

import com.crivano.jflow.model.TaskDefinition;

public class TaskDefinitionSupport
		implements TaskDefinition<TaskKindSupport, ResponsibleKindSupport, VariableSupport, DetourSupport> {

	public TaskDefinitionSupport(String id, TaskKindSupport kind, String title, String after,
			ResponsibleKindSupport responsibleKind, List<VariableSupport> variable, List<DetourSupport> detour,
			String subject, String text) {
		super();
		this.id = id;
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

	private TaskKindSupport kind;

	private String title;

	private String after;

	private ResponsibleKindSupport responsibleKind;

	private List<VariableSupport> variable;

	private List<DetourSupport> detour;

	private String subject;

	private String text;

	@Override
	public String getIdentifier() {
		return id;
	}

	@Override
	public TaskKindSupport getKind() {
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
	public ResponsibleKindSupport getResponsibleKind() {
		return responsibleKind;
	}

	@Override
	public List<VariableSupport> getVariable() {
		return variable;
	}

	@Override
	public List<DetourSupport> getDetour() {
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
