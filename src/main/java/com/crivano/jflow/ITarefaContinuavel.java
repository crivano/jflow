package com.crivano.jflow;

import java.util.Map;

import com.crivano.jflow.model.IDefinicaoDeTarefa;
import com.crivano.jflow.model.IProcedimento;

public interface ITarefaContinuavel extends ITarefa {
	ResultadoDeTarefa continuar(IDefinicaoDeTarefa tarefa, IProcedimento procedimento, Integer indiceDesvio,
			Map<String, Object> parametro);
}
