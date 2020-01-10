package com.crivano.jflow.support;

import com.crivano.jflow.model.TaskDefinitionVariable;
import com.crivano.jflow.model.enm.VariableEditingKind;
import com.crivano.jflow.model.enm.VariableKind;

public class VariableSupport implements TaskDefinitionVariable {

	String titulo;

	String identificador;

	VariableKind tipo;

	VariableEditingKind tipoDeEdicao;

	@Override
	public String getTitle() {
		return titulo;
	}

	@Override
	public String getIdentifier() {
		return identificador;
	}

	@Override
	public VariableKind getKind() {
		return tipo;
	}

	@Override
	public VariableEditingKind getEditingKind() {
		return tipoDeEdicao;
	}
}
