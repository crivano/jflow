package com.crivano.jflow.model.enm;

public enum ProcessInstanceStatus {
	INACTIVE("Inactive"),
	//
	STARTED("Started"),
	//
	PAUSED("Paused"),
	//
	RESUMING("Resuming"),
	//
	FINISHED("Ended");

	private final String descr;

	ProcessInstanceStatus(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return this.descr;
	}
}
