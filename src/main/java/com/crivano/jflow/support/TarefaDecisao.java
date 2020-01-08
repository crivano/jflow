package com.crivano.jflow.support;

import com.crivano.jflow.ITarefa;
import com.crivano.jflow.ResultadoDeTarefa;
import com.crivano.jflow.TipoDeResultadoDeTarefaEnum;
import com.crivano.jflow.model.IDefinicaoDeTarefa;
import com.crivano.jflow.model.IDesvio;
import com.crivano.jflow.model.IProcedimento;

public class TarefaDecisao implements ITarefa {

	@Override
	public ResultadoDeTarefa executar(IDefinicaoDeTarefa tarefa, IProcedimento procedimento) {
		if (tarefa.getDesvio() == null || tarefa.getDesvio().size() == 0)
			return new ResultadoDeTarefa(TipoDeResultadoDeTarefaEnum.CONCLUIDO, null, null, null, null);
		for (IDesvio d : tarefa.getDesvio()) {
			if (d.getCondicao() == null || verificarCondicao(d.getCondicao(), procedimento))
				return new ResultadoDeTarefa(TipoDeResultadoDeTarefaEnum.CONCLUIDO, d.getTarefa(), null, null, null);
		}

		// Nunca deve chegar aqui, pois a última condição deve sempre ser nula
		return new ResultadoDeTarefa(TipoDeResultadoDeTarefaEnum.CONCLUIDO, null, null, null, null);
	}

	private boolean verificarCondicao(String condicao, IProcedimento procedimento) {
		return false;
	}
}
