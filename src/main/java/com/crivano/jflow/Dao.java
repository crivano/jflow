package com.crivano.jflow;

import java.util.List;

import com.crivano.jflow.model.ProcessInstance;

public interface Dao {

	void persist(ProcessInstance pi);

	List<ProcessInstance> listByEvent(String event);

}
