package com.crivano.jflow.support;

public enum TipoDeTarefaEnum {
	AGUARDAR_ASSINATURA_PRINCIPAL("Aguardar Assinatura", "br.com.xrp.wfl.util.TarefaAguardarAssinaturaPrincipal"),
	//
	ENVIAR_PRINCIPAL("Enviar", "br.com.xrp.wfl.util.TarefaEnviarPrincipal"),
	//
	INCLUIR_DOCUMENTO("Incluir Documento", "br.com.xrp.wfl.util.TarefaIncluirDocumentoPrincipal"),
	//
	ARQUIVAR_PRINCIPAL("Arquivar", "br.com.xrp.wfl.util.TarefaArquivarPrincipal"),
	//
	FORMULARIO("Formulário", "br.com.xrp.wfl.util.TarefaFormulario"),
	//
	DECISAO("Decisão", "br.com.xrp.wfl.util.TarefaDecisao"),
	//
	EMAIL("E-mail", "br.com.xrp.wfl.util.TarefaEmail");

	private final String descr;

	private String clazz;

	TipoDeTarefaEnum(String descr, String clazz) {
		this.descr = descr;
		this.clazz = clazz;
	}

	public String getDescr() {
		return this.descr;
	}

	public String getClazz() {
		return this.clazz;
	}
}
