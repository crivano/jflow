package com.crivano.jflow.model;

import com.crivano.jflow.Task;

public interface TaskKind {

	String getDescr();

	Class<? extends Task> getClazz();
	
	String getGraphKind();

	String getGraphTitle();

}