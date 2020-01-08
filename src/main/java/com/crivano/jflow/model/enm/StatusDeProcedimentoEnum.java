package com.crivano.jflow.model.enm;

public enum StatusDeProcedimentoEnum {
	INATIVO("Inativo"),
	//
	INICIADO("Iniciado"),
	//
	AGUARDANDO("Aguardando"),
	//
	PROSSEGUINDO("Prosseguindo"),
	//
	PAUSADO("Pausado"),
	//
	FINALIZADO("Finalizado");

	private final String descr;

	StatusDeProcedimentoEnum(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return this.descr;
	}
}
