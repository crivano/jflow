package com.crivano.jflow.model.enm;

public enum TipoDeVariavelDeProcedimentoEnum {
	STRING("String"),
	//
	DATE("Data"),
	//
	BOOLEAN("Booleana");

	private final String descr;

	TipoDeVariavelDeProcedimentoEnum(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return this.descr;
	}
}