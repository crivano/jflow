package com.crivano.jflow.support;

import com.crivano.jflow.model.IVariavel;
import com.crivano.jflow.model.enm.TipoDeEdicaoDeVariavelDeProcedimentoEnum;
import com.crivano.jflow.model.enm.TipoDeVariavelDeProcedimentoEnum;

public class Variavel implements IVariavel {

	String titulo;

	String identificador;

	TipoDeVariavelDeProcedimentoEnum tipo;

	TipoDeEdicaoDeVariavelDeProcedimentoEnum tipoDeEdicao;

	@Override
	public String getTitulo() {
		return titulo;
	}

	@Override
	public String getIdentificador() {
		return identificador;
	}

	@Override
	public TipoDeVariavelDeProcedimentoEnum getTipo() {
		return tipo;
	}

	@Override
	public TipoDeEdicaoDeVariavelDeProcedimentoEnum getTipoDeEdicao() {
		return tipoDeEdicao;
	}
}
