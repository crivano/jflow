package com.crivano.jflow.model.enm;

public enum TipoDeEdicaoDeVariavelDeProcedimentoEnum {
	LEITURA_E_ESCRITA("Leitura e Escrita"),
	//
	APENAS_LEITURA("Apenas Leitura");

	private final String descr;

	TipoDeEdicaoDeVariavelDeProcedimentoEnum(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return this.descr;
	}
}
