package com.crivano.jflow.model;

import java.util.Date;

public interface IVariavelDeProcedimento {

	String getId();

	void setId(String id);

	String getString();

	void setString(String string);

	Date getDate();

	void setDate(Date date);

	Boolean getBool();

	void setBool(Boolean bool);

	Double getNumber();

	void setNumber(Double number);

}