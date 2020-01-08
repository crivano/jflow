package com.crivano.jflow.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.crivano.jflow.model.enm.StatusDeProcedimentoEnum;
import com.crivano.jflow.support.VariavelDeProcedimento;

public interface IProcedimento {

	void iniciar();

	void aguardar(String evento, IParte responsavel);

	void prosseguir();

	void finalizar();

	IDefinicaoDeTarefa getTarefaCorrente();

	IDefinicaoDeTarefa getTarefaPorIndice(int i);

	// Se for "fim" retorna length + 1
	int getIndicePorId(String id);

	void onLoad();

	void onSave() throws Exception;

	IParte calcularResponsavel(IDefinicaoDeTarefa tarefa);

	String getCodigo();

	IDefinicaoDeProcedimento getDefinicao();

	IRef<IPrincipal> getRefPrincipal();

	Integer getIndiceCorrente();

	Map<String, Object> getVariavel();

	List<VariavelDeProcedimento> getListaDeVariaveis();

	StatusDeProcedimentoEnum getStatus();

	Date getDtEvento();

	String getEvento();

	IParte getResponsavel();

	void setIndiceCorrente(int indiceCorrente);

	void setDefinicao(IDefinicaoDeProcedimento definicao);

	void setRefPrincipal(IRef<IPrincipal> refPrincipal);

	void setVariavel(Map<String, Object> variavel);

}