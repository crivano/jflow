package com.crivano.jflow.model;

import com.crivano.jflow.model.enm.VariableEditingKind;
import com.crivano.jflow.model.enm.VariableKind;

public interface TaskDefinitionVariable {

	String getTitle();

	String getIdentifier();

	VariableKind getKind();

	VariableEditingKind getEditingKind();

}