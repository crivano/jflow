package com.crivano.jflow.model.util;

import java.util.Map;

import com.crivano.jflow.model.IDefinicaoDeProcedimento;
import com.crivano.jflow.model.IMotorDeProcedimento;
import com.crivano.jflow.model.IPrincipal;
import com.crivano.jflow.model.IProcedimento;
import com.crivano.jflow.model.IRef;

public class MotorDeProcedimentoProxy implements IMotorDeProcedimento {
	private static MotorDeProcedimentoProxy uniqueInstance = new MotorDeProcedimentoProxy();
	private IMotorDeProcedimento delegate = null;

	private MotorDeProcedimentoProxy() {
	}

	public static MotorDeProcedimentoProxy getInstance() {
		return uniqueInstance;
	}

	public void setDelegate(IMotorDeProcedimento d) {
		delegate = d;
	}

	@Override
	public void iniciar(IProcedimento pi, IDefinicaoDeProcedimento definicao, IRef<IPrincipal> principal,
			Map<String, Object> variavel) throws Exception {
		if (delegate == null)
			return;
		delegate.iniciar(pi, definicao, principal, variavel);
	}

	@Override
	public int sinalizar(String namespace, String evento, Integer indiceDesvio, Map<String, Object> parametro)
			throws Exception {
		if (delegate == null)
			return 0;
		return delegate.sinalizar(namespace, evento, indiceDesvio, parametro);
	}

}
