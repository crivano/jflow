package com.crivano.jflow.model;

import java.util.Map;

public interface IMotorDeProcedimento {

	void iniciar(IProcedimento pi, IDefinicaoDeProcedimento definicao, IRef<IPrincipal> principal,
			Map<String, Object> variavel) throws Exception;

	int sinalizar(String namespace, String evento, Integer indiceDesvio, Map<String, Object> parametro)
			throws Exception;
}