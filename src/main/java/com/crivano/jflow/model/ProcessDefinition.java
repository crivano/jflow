package com.crivano.jflow.model;

import java.util.List;

public interface ProcessDefinition<TD extends TaskDefinition<?, ?, ?, ?>> {

	List<TD> getTaskDefinition();

}