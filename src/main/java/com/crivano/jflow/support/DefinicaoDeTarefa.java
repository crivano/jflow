package com.crivano.jflow.support;

import java.util.List;

import com.crivano.jflow.model.IDefinicaoDeTarefa;
import com.crivano.jflow.model.IRef;
import com.crivano.jflow.model.ITipoDeResponsavelPorTarefaEnum;

public class DefinicaoDeTarefa implements IDefinicaoDeTarefa {

	public DefinicaoDeTarefa(String id, String nome, TipoDeTarefaEnum tipo, String titulo, String depois,
			ITipoDeResponsavelPorTarefaEnum tipoResponsavel, IRef refUnidadeResponsavel, IRef refPapelResponsavel,
			IRef refPessoaResponsavel, IRef refTipologia, List<Variavel> variavel, List<Desvio> desvio, String assunto,
			String texto) {
		super();
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
		this.titulo = titulo;
		this.depois = depois;
		this.tipoResponsavel = tipoResponsavel;
		this.refUnidadeResponsavel = refUnidadeResponsavel;
		this.refPapelResponsavel = refPapelResponsavel;
		this.refPessoaResponsavel = refPessoaResponsavel;
		this.refTipologia = refTipologia;
		this.variavel = variavel;
		this.desvio = desvio;
		this.assunto = assunto;
		this.texto = texto;
	}

	public DefinicaoDeTarefa() {
		super();
	}

	String id;

	String nome;

	private TipoDeTarefaEnum tipo;

	private String titulo;

	private String depois;

	private ITipoDeResponsavelPorTarefaEnum tipoResponsavel;

	private IRef refUnidadeResponsavel;

	private IRef refPapelResponsavel;

	private IRef refPessoaResponsavel;

	private IRef refTipologia;

	private List<Variavel> variavel;

	private List<Desvio> desvio;

	private String assunto;

	private String texto;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public TipoDeTarefaEnum getTipo() {
		return tipo;
	}

	@Override
	public String getTitulo() {
		return titulo;
	}

	@Override
	public String getDepois() {
		return depois;
	}

	@Override
	public ITipoDeResponsavelPorTarefaEnum getTipoResponsavel() {
		return tipoResponsavel;
	}

	@Override
	public IRef getRefUnidadeResponsavel() {
		return refUnidadeResponsavel;
	}

	@Override
	public IRef getRefPapelResponsavel() {
		return refPapelResponsavel;
	}

	@Override
	public IRef getRefPessoaResponsavel() {
		return refPessoaResponsavel;
	}

	@Override
	public IRef getRefTipologia() {
		return refTipologia;
	}

	@Override
	public List<Variavel> getVariavel() {
		return variavel;
	}

	@Override
	public List<Desvio> getDesvio() {
		return desvio;
	}

	@Override
	public String getAssunto() {
		return assunto;
	}

	@Override
	public String getTexto() {
		return texto;
	}

}
