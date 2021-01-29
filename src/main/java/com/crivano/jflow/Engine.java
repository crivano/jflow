package com.crivano.jflow;

import java.util.Map;

import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.Responsible;

public interface Engine<PI extends ProcessInstance<?, ?, ?>, R extends Responsible, H extends Handler<PI, R>> {

	void start(PI pi) throws Exception;

	int resume(String event, Integer detourIndex, Map<String, Object> param) throws Exception;

	H getHandler();

	TaskResult execute(PI pi, Integer from, int to) throws Exception;

}