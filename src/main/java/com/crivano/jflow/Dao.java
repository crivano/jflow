package com.crivano.jflow;

import java.util.List;

import com.crivano.jflow.model.ProcessInstance;

public interface Dao<PI extends ProcessInstance<?, ?, ?>> {

	void persist(PI pi);

	List<PI> listByEvent(String event);

}
