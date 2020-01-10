package com.crivano.jflow.model.enm;

public enum TaskResultKind {
	DONE("Done"),
	//
	ERROR("Error"),
	//
	PAUSE("Pause");

	private final String descr;

	TaskResultKind(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return this.descr;
	}
}