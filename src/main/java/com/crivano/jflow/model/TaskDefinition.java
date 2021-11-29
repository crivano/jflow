package com.crivano.jflow.model;

import java.util.List;

public interface TaskDefinition<TK extends TaskKind, RK extends ResponsibleKind, DV extends TaskDefinitionVariable, DD extends TaskDefinitionDetour> {

	String getIdentifier();

	TK getKind();

	String getTitle();

	String getAfter();

	RK getResponsibleKind();

	List<DV> getVariable();

	List<DD> getDetour();

	String getSubject();

	String getText();

	String getResponsibleDescription();

	String getTooltip();

}