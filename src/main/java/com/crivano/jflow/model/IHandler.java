package com.crivano.jflow.model;

import com.crivano.jflow.ResultadoDeTarefa;

public interface IHandler {
	void afterPause(IProcedimento pi, ResultadoDeTarefa result);
}
