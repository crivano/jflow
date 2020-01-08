package com.crivano.jflow.model;

import java.util.List;

import com.crivano.jflow.support.Desvio;
import com.crivano.jflow.support.TipoDeTarefaEnum;
import com.crivano.jflow.support.Variavel;

public interface IDefinicaoDeTarefa {

	String getId();

	String getNome();

	TipoDeTarefaEnum getTipo();

	String getTitulo();

	String getDepois();

	ITipoDeResponsavelPorTarefaEnum getTipoResponsavel();

	IRef getRefUnidadeResponsavel();

	IRef getRefPapelResponsavel();

	IRef getRefPessoaResponsavel();

	IRef getRefTipologia();

	List<Variavel> getVariavel();

	List<Desvio> getDesvio();

	String getAssunto();

	String getTexto();

}