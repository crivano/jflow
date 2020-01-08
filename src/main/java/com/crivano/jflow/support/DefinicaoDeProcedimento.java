package com.crivano.jflow.support;

import java.util.ArrayList;
import java.util.List;

import com.crivano.jflow.model.IDefinicaoDeProcedimento;

public class DefinicaoDeProcedimento implements IDefinicaoDeProcedimento {
	private List<DefinicaoDeTarefa> tarefa = new ArrayList<>();

	@Override
	public List<DefinicaoDeTarefa> getTarefa() {
		return tarefa;
	}
}
