package com.crivano.jflow.support;

import com.crivano.jflow.model.Responsible;

public class ResponsibleSupport implements Responsible {
	String initials;
	String email;

	public ResponsibleSupport(String initials, String email) {
		this.initials = initials;
		this.email = email;
	}

	@Override
	public String getInitials() {
		return initials;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getTooltip() {
		// TODO Auto-generated method stub
		return null;
	}
}