package com.crivano.jflow.model.util;

public class MissingParameterException extends RuntimeException {
	String parameter;

	public MissingParameterException(String parameter) {
		super("Parameter '" + parameter + "' is missing");
		this.parameter = parameter;
	}
}
