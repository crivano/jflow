package com.crivano.jflow.model.enm;

public enum VariableKind {
	STRING("String"),
	//
	DATE("Date"),
	//
	DOUBLE("Number"),
	//
	BOOLEAN("Boolean");

	private final String descr;

	VariableKind(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return this.descr;
	}
}