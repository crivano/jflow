package com.crivano.jflow;

import com.crivano.jflow.model.IDefinicaoDeTarefa;
import com.crivano.jflow.model.IProcedimento;

public interface ITarefa {
	ResultadoDeTarefa executar(IDefinicaoDeTarefa tarefa, IProcedimento procedimento) throws Exception;
}
