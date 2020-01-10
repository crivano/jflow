package com.crivano.jflow.support;

import com.crivano.jflow.model.TaskDefinitionDetour;

public class DetourSupport implements TaskDefinitionDetour {
	String titulo;

	String tarefa;

	String condicao;

	public DetourSupport(String titulo, String tarefa, String condicao) {
		this.titulo = titulo;
		this.tarefa = tarefa;
		this.condicao = condicao;
	}

	@Override
	public String getTitle() {
		return titulo;
	}

	@Override
	public String getTaskId() {
		return tarefa;
	}

	@Override
	public String getCondition() {
		return condicao;
	}

	@Override
	public void setTitle(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public void setTaskId(String tarefa) {
		this.tarefa = tarefa;
	}

	@Override
	public void setCondition(String condicao) {
		this.condicao = condicao;
	}
}