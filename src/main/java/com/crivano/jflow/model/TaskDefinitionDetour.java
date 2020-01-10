package com.crivano.jflow.model;

public interface TaskDefinitionDetour {

	String getTitle();

	String getTaskId();

	String getCondition();

	void setTitle(String title);

	void setTaskId(String taskId);

	void setCondition(String condition);

}