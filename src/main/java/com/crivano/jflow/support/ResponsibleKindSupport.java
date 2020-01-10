package com.crivano.jflow.support;

import com.crivano.jflow.model.ResponsibleKind;

public enum ResponsibleKindSupport implements ResponsibleKind {
	REGISTRANT("Cadastrante"),
	//
	REGISTRANT_UNIT("Unidade do Cadastrante"),
	//
	//
	UNIT("Unidade"),
	//
	PERSON("Pessoa");

	private final String descr;

	ResponsibleKindSupport(String descr) {
		this.descr = descr;
	}

	@Override
	public String getDescr() {
		return this.descr;
	}
}
