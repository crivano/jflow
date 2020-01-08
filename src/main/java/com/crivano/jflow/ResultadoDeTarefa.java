package com.crivano.jflow;

import com.crivano.jflow.model.IParte;

public class ResultadoDeTarefa {
	public ResultadoDeTarefa(TipoDeResultadoDeTarefaEnum tipo, String desvio, Throwable erro, String evento,
			IParte responsavel) {
		super();
		this.tipo = tipo;
		this.desvio = desvio;
		this.erro = erro;
		this.evento = evento;
		this.responsavel = responsavel;
	}

	TipoDeResultadoDeTarefaEnum tipo;
	String desvio;
	Throwable erro;
	String evento;
	IParte responsavel;

	public TipoDeResultadoDeTarefaEnum getTipo() {
		return tipo;
	}

	public String getDesvio() {
		return desvio;
	}

	public Throwable getErro() {
		return erro;
	}

	public String getEvento() {
		return evento;
	}

	public IParte getResponsavel() {
		return responsavel;
	}
}
