package com.crivano.jflow.model;

import com.crivano.jflow.model.enm.TipoDeEdicaoDeVariavelDeProcedimentoEnum;
import com.crivano.jflow.model.enm.TipoDeVariavelDeProcedimentoEnum;

public interface IVariavel {

	String getTitulo();

	String getIdentificador();

	TipoDeVariavelDeProcedimentoEnum getTipo();

	TipoDeEdicaoDeVariavelDeProcedimentoEnum getTipoDeEdicao();

}