package com.crivano.jflow;

public enum TipoDeResultadoDeTarefaEnum {
	CONCLUIDO("Concluído"),
	//
	ERRO("Erro"),
	//
	AGUARDAR("Aguardar");

	private final String descr;

	TipoDeResultadoDeTarefaEnum(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return this.descr;
	}
}