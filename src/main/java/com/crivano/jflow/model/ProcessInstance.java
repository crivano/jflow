package com.crivano.jflow.model;

import java.util.Date;
import java.util.Map;

import com.crivano.jflow.model.enm.ProcessInstanceStatus;

public interface ProcessInstance {

	void start();

	void pause(String event, Responsible responsible);

	void resume();

	void end();

	TaskDefinition getCurrentTaskDefinition();

	TaskDefinition getTaskDefinitionByIndex(int i);

	// Se for "fim" retorna length + 1
	int getIndexById(String id);

	Responsible calcResponsible(TaskDefinition td);

	String getCode();

	ProcessDefinition getProcessDefinition();

	Integer getCurrentIndex();

	Map<String, Object> getVariable();

	ProcessInstanceStatus getStatus();

	Date getDtEvent();

	String getEvent();

	Responsible getResponsible();

	void setCurrentIndex(int idx);

	void setProcessDefinition(ProcessDefinition pd);

	void setVariable(Map<String, Object> variable);

}