package com.crivano.jflow.model;

import java.util.Date;
import java.util.Map;

import com.crivano.jflow.model.enm.ProcessInstanceStatus;

public interface ProcessInstance<PD extends ProcessDefinition<TD>, TD extends TaskDefinition<?, ?, ?, ?>, R extends Responsible> {

	void start();

	void pause(String event, R responsible);

	void resume();

	void end();

	TD getCurrentTaskDefinition();

	TD getTaskDefinitionByIndex(int i);

	// Se for "fim" retorna length + 1
	int getIndexById(String id);

	R calcResponsible(TD td);

	//String getCode();

	PD getProcessDefinition();

	Integer getCurrentIndex();

	Map<String, Object> getVariable();

	ProcessInstanceStatus getStatus();

	Date getDtEvent();

	String getEvent();

	R getResponsible();

	void setCurrentIndex(int idx);

	void setProcessDefinition(PD pd);

	void setVariable(Map<String, Object> variable);

}