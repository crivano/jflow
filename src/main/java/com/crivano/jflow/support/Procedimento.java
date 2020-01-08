package com.crivano.jflow.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.crivano.jflow.model.IDefinicaoDeProcedimento;
import com.crivano.jflow.model.IDefinicaoDeTarefa;
import com.crivano.jflow.model.IParte;
import com.crivano.jflow.model.IPrincipal;
import com.crivano.jflow.model.IProcedimento;
import com.crivano.jflow.model.IRef;
import com.crivano.jflow.model.enm.StatusDeProcedimentoEnum;

public abstract class Procedimento implements IProcedimento {
	private String codigo;

	private IDefinicaoDeProcedimento definicao;

	private IRef<IPrincipal> refPrincipal;

	private Integer indiceCorrente = null;

	private Map<String, Object> variavel = new TreeMap<>();

	private List<VariavelDeProcedimento> listaDeVariaveis = new ArrayList<>();

	private StatusDeProcedimentoEnum status = StatusDeProcedimentoEnum.INATIVO;

	private Date dtEvento;

	private String evento;

	IParte responsavel;

	public Procedimento(IDefinicaoDeProcedimento definicao, IRef<IPrincipal> refPrincipal,
			Map<String, Serializable> variavel) {
		this.definicao = definicao;
		if (variavel != null)
			this.variavel.putAll(variavel);
		if (refPrincipal != null) {
			this.refPrincipal = refPrincipal;
			this.variavel.put("_keyPrincipal", this.refPrincipal.toString());
			this.variavel.put("_codPrincipal", refPrincipal.get().getCode());
		}
	}

	@Override
	public void iniciar() {
		indiceCorrente = -1;
		status = StatusDeProcedimentoEnum.INICIADO;
	}

	@Override
	public void aguardar(String evento, IParte responsavel) {
		this.evento = evento;
		this.responsavel = responsavel;
		this.dtEvento = new Date();
		status = StatusDeProcedimentoEnum.AGUARDANDO;
	}

	@Override
	public void prosseguir() {
		this.evento = null;
		this.responsavel = null;
		this.dtEvento = null;
		status = StatusDeProcedimentoEnum.PROSSEGUINDO;
	}

	@Override
	public void finalizar() {
		indiceCorrente = null;
		status = StatusDeProcedimentoEnum.FINALIZADO;
	}

	@Override
	public IDefinicaoDeTarefa getTarefaCorrente() {
		return getTarefaPorIndice(indiceCorrente);
	}

	@Override
	public IDefinicaoDeTarefa getTarefaPorIndice(int i) {
		if (i < 0 || i >= definicao.getTarefa().size())
			return null;
		return definicao.getTarefa().get(i);
	}

	// Se for "fim" retorna length + 1
	@Override
	public int getIndicePorId(String id) {
		int i = 0;
		for (IDefinicaoDeTarefa td : definicao.getTarefa()) {
			if (td.getId().equals(id))
				return i;
			i++;
		}
		return i;
	}

	@Override
	public void onLoad() {
		variavel.clear();
		for (VariavelDeProcedimento v : listaDeVariaveis) {
			if (v.number != null)
				variavel.put(v.id, v.number);
			else if (v.bool != null)
				variavel.put(v.id, v.bool);
			else if (v.date != null)
				variavel.put(v.id, v.date);
			else
				variavel.put(v.id, v.string);
		}
	}

	@Override
	public void onSave() throws Exception {
		listaDeVariaveis.clear();
		for (String k : variavel.keySet()) {
			VariavelDeProcedimento v = new VariavelDeProcedimento();
			v.id = k;
			Object o = variavel.get(k);
			if (o instanceof Boolean)
				v.bool = (Boolean) o;
			else if (o instanceof Double)
				v.number = (Double) o;
			else if (o instanceof Date)
				v.date = (Date) o;
			else
				v.string = o.toString();
			listaDeVariaveis.add(v);
		}
	}

	@Override
	public abstract IParte calcularResponsavel(IDefinicaoDeTarefa tarefa);

	@Override
	public String getCodigo() {
		return codigo;
	}

	@Override
	public IDefinicaoDeProcedimento getDefinicao() {
		return definicao;
	}

	@Override
	public IRef<IPrincipal> getRefPrincipal() {
		return refPrincipal;
	}

	@Override
	public Integer getIndiceCorrente() {
		return indiceCorrente;
	}

	@Override
	public Map<String, Object> getVariavel() {
		return variavel;
	}

	@Override
	public List<VariavelDeProcedimento> getListaDeVariaveis() {
		return listaDeVariaveis;
	}

	@Override
	public StatusDeProcedimentoEnum getStatus() {
		return status;
	}

	@Override
	public Date getDtEvento() {
		return dtEvento;
	}

	@Override
	public String getEvento() {
		return evento;
	}

	@Override
	public IParte getResponsavel() {
		return responsavel;
	}

	@Override
	public void setIndiceCorrente(int indiceCorrente) {
		this.indiceCorrente = indiceCorrente;
	}

	@Override
	public void setDefinicao(IDefinicaoDeProcedimento definicao) {
		this.definicao = definicao;
	}

	@Override
	public void setRefPrincipal(IRef<IPrincipal> refPrincipal) {
		this.refPrincipal = refPrincipal;
	}

	@Override
	public void setVariavel(Map<String, Object> variavel) {
		this.variavel = variavel;
	}

}
