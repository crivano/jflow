package com.crivano.jflow.model.enm;

public enum VariableEditingKind {
	READ_WRITE("Read and Write"),
	//
	READ_ONLY("Read Only");

	private final String descr;

	VariableEditingKind(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return this.descr;
	}
}
