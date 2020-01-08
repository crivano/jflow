package com.crivano.jflow.support;

import com.crivano.jflow.model.ITipoDeResponsavelPorTarefaEnum;

public enum TipoDeResponsavelPorTarefaEnum implements ITipoDeResponsavelPorTarefaEnum {
	CADASTRANTE("Cadastrante"),
	//
	CADASTRANTE_UNIDADE("Unidade do Cadastrante"),
	//
	SUBSCRITOR("Subscritor"),
	//
	SUBSCRITOR_UNIDADE("Unidade do Subscritor"),
	//
	DESTINATARIO("Destinatário"),
	//
	DESTINATARIO_UNIDADE("Unidade do Destinatário"),
	//
	UNIDADE("Unidade"),
	//
	PAPEL("Papel"),
	//
	PESSOA("Pessoa");

	private final String descr;

	TipoDeResponsavelPorTarefaEnum(String descr) {
		this.descr = descr;
	}

	@Override
	public String getDescr() {
		return this.descr;
	}
}
