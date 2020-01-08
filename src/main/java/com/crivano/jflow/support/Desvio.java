package com.crivano.jflow.support;

import com.crivano.jflow.model.IDesvio;

public class Desvio implements IDesvio {
	String titulo;

	String tarefa;

	String condicao;

	public Desvio(String titulo, String tarefa, String condicao) {
		this.titulo = titulo;
		this.tarefa = tarefa;
		this.condicao = condicao;
	}

	@Override
	public String getTitulo() {
		return titulo;
	}

	@Override
	public String getTarefa() {
		return tarefa;
	}

	@Override
	public String getCondicao() {
		return condicao;
	}

	@Override
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public void setTarefa(String tarefa) {
		this.tarefa = tarefa;
	}

	@Override
	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}
}