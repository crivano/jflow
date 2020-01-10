package com.crivano.jflow.support;

import java.util.Date;

public class ProcessInstanceVariableSupport implements ProcessInstanceVariable {
	String id;
	String string;
	Date date;
	Boolean bool;
	Double number;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getString() {
		return string;
	}

	@Override
	public void setString(String string) {
		this.string = string;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public Boolean getBool() {
		return bool;
	}

	@Override
	public void setBool(Boolean bool) {
		this.bool = bool;
	}

	@Override
	public Double getNumber() {
		return number;
	}

	@Override
	public void setNumber(Double number) {
		this.number = number;
	}
}
